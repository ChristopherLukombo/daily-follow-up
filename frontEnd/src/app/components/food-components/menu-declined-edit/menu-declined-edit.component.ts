import { Component, OnInit } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { MenuDTO } from "src/app/models/dto/food/menuDTO";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";
import { WeekDTO } from "src/app/models/dto/food/weekDTO";
import { HttpErrorResponse } from "@angular/common/http";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-menu-declined-edit",
  templateUrl: "./menu-declined-edit.component.html",
  styleUrls: ["./menu-declined-edit.component.scss"],
})
export class MenuDeclinedEditComponent implements OnInit {
  menu: Menu;

  warning: string;

  error: string;
  creating: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.menu = this.alimentationService.getMenuFromLocal();
    if (!this.menu) {
      this.warning = this.impossibleToGetMenu();
    }
  }

  impossibleToGetMenu(): string {
    return TypeMessage.IMPOSSIBLE_TO_GET_LOCAL_MENU;
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
      null,
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
      null,
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
    dtoFromWeeks.forEach((week) => {
      week.id = null;
      week.days.forEach((day) => {
        day.id = null;
        day.momentDays.forEach((moment) => {
          moment.id = null;
        });
      });
    });
    return dtoFromWeeks;
  }

  onSubmit(): void {
    this.error = null;
    if (!this.weeksAreValid() || !this.replacementIsValid()) {
      this.error = TypeMessage.MENU_FORM_INVALID;
      return;
    }
    let dto = this.getMenuDTO();
    this.creating = true;
    this.alimentationService.createMenu(dto).subscribe(
      (data) => {
        this.toastrService.success(
          "Le menu a bien été créé",
          "Création terminée !"
        );
        this.creating = false;
        this.alimentationService.removeMenuFromLocal();
        this.router.navigate(["/food/menu/currents"]);
      },
      (error) => {
        this.toastrService.error(this.getCustomError(error), "Oops !");
        this.creating = false;
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
    } else if (error && error.status === 500) {
      return error.error.message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
