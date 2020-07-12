import { Component, OnInit } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { HttpErrorResponse } from "@angular/common/http";
import { mergeMap } from "rxjs/operators";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-meals",
  templateUrl: "./meals.component.html",
  styleUrls: ["./meals.component.scss"],
})
export class MealsComponent implements OnInit {
  contents: Content[] = [];
  content: Content;

  error: string;
  loading: boolean = false;

  modeDelete: boolean = false;
  contentsToDelete: Content[] = [];
  btnDelete: string = "Confirmer la suppression";
  confirmDelete: string =
    "Les plats seront supprimés de la clinique. Veuillez confirmer pour continuer.";
  deleting: boolean = false;

  isCaregiver: boolean = false;

  constructor(
    private loginService: LoginService,
    private alimentationService: AlimentationService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.isCaregiver = this.loginService.isCaregiver();
    this.loading = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        this.contents = data;
        this.loading = false;
      },
      (error) => {
        this.error = this.getCustomError(error);
        this.loading = false;
      }
    );
  }

  setContent(content: Content): void {
    this.content = content;
  }

  setModeDelete(value: boolean): void {
    this.contentsToDelete = [];
    if (value) {
      this.modeDelete = true;
    } else {
      this.modeDelete = false;
    }
  }

  onDelete(): void {
    if (this.contentsToDelete.length === 0) {
      this.toastrService.error("Il n'y a aucun plat à supprimer", "Oops !");
      return;
    }
    this.deleting = true;
    this.alimentationService
      .deleteManyContents(this.contentsToDelete)
      .pipe(mergeMap(() => this.alimentationService.getAllContents()))
      .subscribe(
        (data) => {
          this.contents = data;
          this.toastrService.success(
            "Les plats ont bien été supprimés",
            "Suppression réussie !"
          );
          this.deleting = false;
        },
        (error) => {
          this.deleting = false;
          this.toastrService.error(this.getCustomError(error), "Oops !");
        }
      );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 409) {
      return this.removeTriggerTrace(error.error.message);
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }

  /**
   * Retire les notions techniques et les messages provenant directement de la base
   * @param message
   */
  removeTriggerTrace(message: string): string {
    return message.split("Où")[0].replace("ERREUR:", "");
  }
}
