import { Component, OnInit, Input } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { MenuDTO } from "src/app/models/dto/food/menuDTO";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";
import { WeekDTO } from "src/app/models/dto/food/weekDTO";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { TypeMessage } from "src/app/models/utils/message-enum";

@Component({
  selector: "app-declined-menu-template",
  templateUrl: "./declined-menu-template.component.html",
  styleUrls: ["./declined-menu-template.component.scss"],
})
export class DeclinedMenuTemplateComponent implements OnInit {
  @Input() menu: Menu;
  @Input() from: Menu;

  selectedWeek: number;
  moments: string[] = ["Déjeuner", "Dîner"];
  indexOfDays: Map<string, number> = new Map([
    ["Lundi", 0],
    ["Mardi", 1],
    ["Mercredi", 2],
    ["Jeudi", 3],
    ["Vendredi", 4],
    ["Samedi", 5],
    ["Dimanche", 6],
  ]);

  error: string;
  creating: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.menu) this.selectedWeek = this.menu.weeks[0].number;
  }

  selectWeek(numWeek: number): void {
    this.selectedWeek = numWeek;
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

  onConfirm(): void {
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
        this.router.navigate(["/food/menu/currents"]);
      },
      (error) => {
        this.toastrService.error(this.getCustomError(error), "Oops !");
        this.creating = false;
      }
    );
  }

  onEdit(): void {
    this.alimentationService.storeMenuToLocal(this.menu);
    this.router.navigate(["/food/menu/decline/edit"]);
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
