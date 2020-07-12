import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { HttpErrorResponse } from "@angular/common/http";
import { Menu } from "src/app/models/food/menu";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { Router, ActivatedRoute } from "@angular/router";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { MenuDTO } from "src/app/models/dto/food/menuDTO";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";
import { WeekDTO } from "src/app/models/dto/food/weekDTO";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-menu-edit",
  templateUrl: "./menu-edit.component.html",
  styleUrls: ["./menu-edit.component.scss"],
})
export class MenuEditComponent implements OnInit {
  menu: Menu;

  loading: boolean = false;
  warning: string;
  error: string;

  invalid: string;
  updating: boolean = false;

  btnDelete: string = "Supprimer le menu";
  confirmDelete: string =
    "Le menu sera supprimé de la clinique, et effacé de l'historique des menus. Veuillez confirmer pour continuer.";
  deleting: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private toastrService: ToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.subscribe((params) => {
      this.alimentationService.getMenu(parseInt(params["id"])).subscribe(
        (data) => {
          if (data) {
            this.isInPast(data)
              ? (this.warning = TypeMessage.OLD_MENU_ARE_NOT_EDITABLE)
              : (this.menu = data);
          } else {
            this.warning = TypeMessage.MENU_DOES_NOT_EXIST;
          }
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    });
  }

  isInPast(menu: Menu): boolean {
    return moment().isAfter(moment(menu.startDate));
  }

  /**
   * Permet de savoir si tout les plats de chaque moment/jours de la semaines ont étés renseignés
   * @returns true si valide false si non
   */
  weeksAreValid(): boolean {
    let result = true;
    this.menu.weeks.forEach((week) => {
      week.days.forEach((day) => {
        day.momentDays.forEach((moment) => {
          if (
            !moment.entry ||
            !moment.dish ||
            !moment.garnish ||
            (moment.name !== "Dîner" && !moment.dairyProduct) ||
            !moment.dessert
          ) {
            result = false;
          }
        });
      });
    });
    return result;
  }

  replacementIsValid(): boolean {
    return (
      this.menu.replacement.entries &&
      this.menu.replacement.entries.length > 0 &&
      this.menu.replacement.dishes &&
      this.menu.replacement.dishes.length > 0 &&
      this.menu.replacement.garnishes &&
      this.menu.replacement.garnishes.length > 0 &&
      this.menu.replacement.dairyProducts &&
      this.menu.replacement.dairyProducts.length > 0 &&
      this.menu.replacement.desserts &&
      this.menu.replacement.desserts.length > 0
    );
  }

  getMenuDTO(): MenuDTO {
    return new MenuDTO(
      this.menu.id,
      this.menu.startDate,
      this.menu.endDate,
      this.menu.repetition,
      this.menu.diets,
      this.menu.texture,
      this.getReplacementDTO(),
      null,
      null,
      this.getWeeksDTOFromWeek()
    );
  }

  getReplacementDTO(): ReplacementDTO {
    return new ReplacementDTO(
      this.menu.replacement.id,
      this.menu.replacement.entries,
      this.menu.replacement.dishes,
      this.menu.replacement.garnishes,
      this.menu.replacement.dairyProducts,
      this.menu.replacement.desserts
    );
  }

  getWeeksDTOFromWeek(): WeekDTO[] {
    let dtoFromWeeks: WeekDTO[] = <WeekDTO[]>(
      JSON.parse(JSON.stringify(this.menu.weeks))
    );
    return dtoFromWeeks;
  }

  onSubmit(): void {
    this.invalid = null;
    if (!this.weeksAreValid() || !this.replacementIsValid()) {
      this.error = TypeMessage.MENU_FORM_INVALID;
      return;
    }
    let dto = this.getMenuDTO();
    this.updating = true;
    this.alimentationService.updateMenu(dto).subscribe(
      (data) => {
        this.toastrService.success(
          "Le menu a bien été mis à jour",
          "Mise à jour terminée !"
        );
        this.updating = false;
        this.router.navigate(["/food/menu/all"]);
      },
      (error) => {
        this.toastrService.error(this.getCustomError(error), "Oops !");
        this.updating = false;
      }
    );
  }

  onDelete(): void {
    this.deleting = true;
    this.alimentationService.deleteMenu(this.menu.id).subscribe(
      (data) => {
        this.deleting = false;
        this.toastrService.success(
          "Le menu a bien été supprimé",
          "Suppression réussie !"
        );
        this.router.navigate(["/food/menu/all"]);
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
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 500) {
      return error.error.message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
