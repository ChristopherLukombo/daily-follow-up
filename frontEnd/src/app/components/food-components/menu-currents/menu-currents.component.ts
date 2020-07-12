import { Component, OnInit } from "@angular/core";
import { Diet } from "src/app/models/patient/diet";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import * as moment from "moment";
import { TypeTexture } from "src/app/models/utils/texture-enum";
import { forkJoin } from "rxjs";
import { Menu } from "src/app/models/food/menu";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-menu-currents",
  templateUrl: "./menu-currents.component.html",
  styleUrls: ["./menu-currents.component.scss"],
})
export class MenuCurrentsComponent implements OnInit {
  textures: string[] = [TypeTexture.NORMAL, TypeTexture.MIXED];
  selectedTexture: string = this.textures[0];
  diets: Diet[] = [];
  selectedDiet: string;

  currentsMenus: Menu[] = [];
  filter: Menu[] = [];
  selectedMenu: Menu;

  dateOfTheDay: string;

  loading: boolean = false;
  error: string;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    moment.locale();
    this.dateOfTheDay = moment().locale("fr").format("dddd Do MMMM YYYY");
    this.loading = true;
    let allDiets = this.alimentationService.getAllDiets();
    let allCurrentsMenus = this.alimentationService.getCurrentsMenus();
    forkJoin([allDiets, allCurrentsMenus]).subscribe(
      (datas) => {
        this.diets = datas[0];
        this.currentsMenus = datas[1];
        this.selectDiet(this.diets[0]);
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  selectTexture(texture: string): void {
    this.selectedTexture = texture;
    this.getCurrentsMenus(this.selectedTexture, this.selectedDiet);
  }

  selectDiet(diet: Diet): void {
    this.selectedDiet = diet.name;
    this.getCurrentsMenus(this.selectedTexture, this.selectedDiet);
  }

  getCurrentsMenus(texture: string, diet: string): void {
    if (!this.currentsMenus) return;
    this.filter = this.currentsMenus.filter(
      (menu) => menu.texture === texture && menu.diets.includes(diet)
    );
    this.selectedMenu = this.filter[0];
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
}
