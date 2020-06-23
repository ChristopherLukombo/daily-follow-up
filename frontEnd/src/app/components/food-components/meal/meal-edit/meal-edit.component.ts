import { Component, OnInit } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { ActivatedRoute } from "@angular/router";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";

@Component({
  selector: "app-meal-edit",
  templateUrl: "./meal-edit.component.html",
  styleUrls: ["./meal-edit.component.scss"],
})
export class MealEditComponent implements OnInit {
  content: Content;

  loading: boolean = false;
  warning: string;
  error: string;

  constructor(
    private route: ActivatedRoute,
    private alimentationService: AlimentationService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.subscribe((params) => {
      this.alimentationService.getContent(parseInt(params["id"])).subscribe(
        (data) => {
          data ? (this.content = data) : this.contentDoesNotExist();
          this.loading = false;
        },
        (error) => {
          this.catchError(error);
          this.loading = false;
        }
      );
    });
  }

  contentDoesNotExist() {
    this.warning =
      "Ce plat n'éxiste pas, ou a été supprimé du stock de nourriture de la clinique. Veuillez réessayer.";
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
