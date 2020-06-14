import { Component, OnInit } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Diet } from "src/app/models/patient/diet";
import { forkJoin } from "rxjs";
import { DeclinatorService } from "src/app/services/alimentation/declinator.service";
import { Content } from "src/app/models/food/content";

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

  allContents: Content[] = [];

  error: string;
  loading: boolean = false;

  cannotDecline: string;

  newMenu: Menu;

  constructor(
    private alimentationService: AlimentationService,
    private declinatorService: DeclinatorService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    let allMenus = this.alimentationService.getAllMenus();
    let allDiets = this.alimentationService.getAllDiets();
    // TODO : récupérer uniquement les menus 'Normal' dans le futur
    forkJoin([allMenus, allDiets]).subscribe(
      (datas) => {
        this.menus = datas[0];
        this.diets = datas[1].filter(
          (d) => !d.name.toLowerCase().includes("normal")
        );
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
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

  onDecline(): void {
    this.cannotDecline = null;
    this.newMenu = null;
    console.log(this.selectedDiets);
    if (this.selectedDiets.every((diet) => !diet.elementsToCheck.size)) {
      this.cannotDecline =
        "Le menu ne peut pas être automatiquement adapté au(s) régime(s) séléctionné(s), veuillez le faire manuellement.";
      return;
    }
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        this.allContents = data;
        let declined: Menu = this.declinatorService.declineMenuForDiets(
          this.selectedMenu,
          this.selectedDiets,
          this.allContents
        );
        this.newMenu = declined;
        console.log(this.newMenu.replacement);
      },
      (error) => {
        this.error = this.getError(error);
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
