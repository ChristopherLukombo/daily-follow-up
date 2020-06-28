import { Component, OnInit } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Diet } from "src/app/models/patient/diet";
import { forkJoin } from "rxjs";
import { DeclinatorService } from "src/app/services/alimentation/declinator.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-menu-decline",
  templateUrl: "./menu-decline.component.html",
  styleUrls: ["./menu-decline.component.scss"],
})
export class MenuDeclineComponent implements OnInit {
  menus: Menu[] = [];
  diets: Diet[] = [];
  selectedMenu: Menu;
  selectedDiets: Diet[] = [];

  error: string;
  loading: boolean = false;

  declining: boolean = false;
  cannotDecline: string;

  newMenu: Menu;

  constructor(
    private alimentationService: AlimentationService,
    private declinatorService: DeclinatorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllMenus().subscribe(
      (data) => {
        this.menus = data;
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  getDeclinableDiets(selected: Menu): void {
    this.alimentationService.getAllDiets().subscribe(
      (data) => {
        this.diets = data.filter((d) => !selected.diets.includes(d.name));
      },
      (error) => {
        console.log(error);
      }
    );
  }

  selectOrDeselectDiet(diet: Diet): void {
    if (this.selectedDiets.find((d) => d.name === diet.name)) {
      this.selectedDiets = this.selectedDiets.filter(
        (d) => d.name !== diet.name
      );
    } else {
      this.selectedDiets.push(diet);
    }
  }

  containsDiet(diet: Diet): boolean {
    return this.selectedDiets.find((d) => d.name === diet.name) ? true : false;
  }

  onEdit(): void {
    this.selectedMenu.diets = this.selectedDiets.map((diet) => diet.name);
    this.selectedMenu.replacement.id = null;
    this.alimentationService.storeMenuToLocal(this.selectedMenu);
    this.router.navigate(["/food/menu/decline/edit"]);
  }

  onDecline(): void {
    this.cannotDecline = null;
    this.newMenu = null;
    if (this.selectedDiets.every((diet) => !diet.elementsToCheck.size)) {
      this.cannotDecline =
        "Le menu ne peut pas être automatiquement adapté au(s) régime(s) séléctionné(s), veuillez le faire manuellement.";
      return;
    }
    this.declining = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        this.newMenu = this.declinatorService.declineMenuForDiets(
          this.selectedMenu,
          this.selectedDiets,
          data
        );
        this.declining = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.declining = false;
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
