import { Component, OnInit } from "@angular/core";
import { Texture } from "src/app/models/food/texture";
import { Diet } from "src/app/models/patient/diet";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import * as moment from "moment";

@Component({
  selector: "app-menu-currents",
  templateUrl: "./menu-currents.component.html",
  styleUrls: ["./menu-currents.component.scss"],
})
export class MenuCurrentsComponent implements OnInit {
  textures: string[] = ["Normal", "Mixé"];
  selectedTexture: string = this.textures[0];
  diets: Diet[] = [];
  selectedDiet: string;

  dateOfTheDay: string;

  loading: boolean = false;
  error: string;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    moment.locale();
    this.dateOfTheDay = moment().locale("fr").format("dddd Do MMMM YYYY");
    this.loading = true;
    this.alimentationService.getAllDiets().subscribe(
      (data) => {
        this.diets = data;
        this.selectedDiet = this.diets[0].name;
      },
      (error) => {
        this.error = this.getError(error);
      },
      () => {
        this.loading = false;
      }
    );
  }

  selectTexture(texture: string): void {
    this.selectedTexture = texture;
  }

  selectDiet(diet: Diet): void {
    this.selectedDiet = diet.name;
  }

  getCurrentMenu(texture: string, diet: string): void {}

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
