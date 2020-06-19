import { Component, OnInit } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";

@Component({
  selector: "app-menu-declined-edit",
  templateUrl: "./menu-declined-edit.component.html",
  styleUrls: ["./menu-declined-edit.component.scss"],
})
export class MenuDeclinedEditComponent implements OnInit {
  menu: Menu;
  textures: string[] = ["Normal", "Mixé"];

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
    if (this.menu) {
      this.createForm();
    } else {
      this.warning = this.impossibleToGetMenu();
    }
  }

  createForm() {}

  impossibleToGetMenu(): string {
    return "Impossible de charger le menu à modifier";
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
          moment.contents.forEach((content) => {
            if (!content) {
              result = false;
            }
          });
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
      this.menu.replacement.starchyFoods &&
      this.menu.replacement.starchyFoods.length > 0 &&
      this.menu.replacement.vegetables &&
      this.menu.replacement.vegetables.length > 0 &&
      this.menu.replacement.dairyProducts &&
      this.menu.replacement.dairyProducts.length > 0 &&
      this.menu.replacement.desserts &&
      this.menu.replacement.desserts.length > 0
    );
  }

  onSubmit(): void {
    this.error = null;
    if (!this.weeksAreValid() || !this.replacementIsValid()) {
      this.error =
        "Le menu est incomplet. Veuillez vérifier la carte de remplacement ainsi que vos insertions pour chaque jour.";
      return;
    }
    console.log(this.menu);
  }
}
