import { Component, OnInit } from "@angular/core";
import { TypeTexture } from "src/app/models/utils/texture-enum";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Menu } from "src/app/models/food/menu";
import * as moment from "moment";

@Component({
  selector: "app-menu-all",
  templateUrl: "./menu-all.component.html",
  styleUrls: ["./menu-all.component.scss"],
})
export class MenuAllComponent implements OnInit {
  times: string[] = ["Anciens menus", "Menus à venir"];
  selectedTime: string = this.times[0];

  timeSlots: string[] = [];
  selectedTimeSlot: string;

  textures: string[] = [TypeTexture.NORMAL, TypeTexture.MIXED];
  selectedTexture: string = this.textures[0];

  menus: Menu[] = [];
  menusByPeriod: Menu[] = [];
  selectedMenu: Menu;

  loading: boolean = false;
  error: string;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.selectTime(this.times[0]);
  }

  // TODO : appellez les anciens menus / futurs menus
  selectTime(time: string): void {
    this.selectedTime = time;
    this.resetDatas();
    this.loading = true;
    this.alimentationService.getAllMenus().subscribe(
      (data) => {
        this.menus = data;
        this.generateTimeSlot(data);
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  generateTimeSlot(menus: Menu[]): void {
    this.timeSlots = menus
      .map((menu) => this.getTimeSlot(menu.startDate, menu.endDate))
      .filter(function (element, index, items) {
        return index === items.indexOf(element);
      });
  }

  getTimeSlot(start: string, end: string): string {
    return (
      "du " +
      moment(start).format("DD/MM/YYYY") +
      " au " +
      moment(end).format("DD/MM/YYYY")
    );
  }

  getPeriodFromTimeSlot(timeSlot: string): string {
    let start: string = timeSlot.split(" ")[1];
    let end: string = timeSlot.split(" ")[3];
    return (
      moment(start, "DD/MM/YYYY").format("YYYY-MM-DD") +
      "_" +
      moment(end, "DD/MM/YYYY").format("YYYY-MM-DD")
    );
  }

  // TODO : filter tout les menus en fonction de la datebegin-date-end choisit
  selectTimeSlot(timeSlot: string): void {
    this.resetDatas();
    this.selectedTimeSlot = timeSlot;
    this.getMenusByTimeSlot(this.selectedTimeSlot, this.selectedTexture);
  }

  selectTexture(texture: string): void {
    this.selectedTexture = texture;
    this.getMenusByTimeSlot(this.selectedTimeSlot, this.selectedTexture);
  }

  getMenusByTimeSlot(timeSlot: string, texture: string): void {
    let period: string = this.getPeriodFromTimeSlot(timeSlot);
    let start: string = period.split("_")[0];
    let end: string = period.split("_")[1];
    this.menusByPeriod = this.menus.filter(
      (menu) =>
        menu.startDate === start &&
        menu.endDate === end &&
        menu.texture === texture
    );
  }

  resetDatas(): void {
    this.selectedTimeSlot = null;
    this.selectedMenu = null;
  }

  isInPast(): boolean {
    return moment().isAfter(moment(this.selectedMenu.endDate));
  }

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
