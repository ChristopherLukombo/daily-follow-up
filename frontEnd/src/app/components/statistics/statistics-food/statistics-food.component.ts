import { Component, OnInit } from "@angular/core";
import { OrdersPerDay } from "src/app/models/statistics/orders-per-day";
import { TopTrendyMenu } from "src/app/models/statistics/top-trendy-menu";
import { StatisticsService } from "src/app/services/statistics/statistics.service";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { OrderService } from "src/app/services/order/order.service";
import * as moment from "moment";
import { Status } from "src/app/models/utils/status-enum";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { forkJoin } from "rxjs";
import { Order } from "src/app/models/patient/order";
import { Content } from "src/app/models/food/content";
import { Menu } from "src/app/models/food/menu";
import { TypeTexture } from "src/app/models/utils/texture-enum";

/**
 * @author christopher
 * @version 17
 */
@Component({
  selector: "app-statistics-food",
  templateUrl: "./statistics-food.component.html",
  styleUrls: ["./statistics-food.component.scss"],
})
export class StatisticsFoodComponent implements OnInit {
  error: string;

  orderPerStatus: Map<string, Array<OrdersPerDay>>;
  topTrendyMenus: Array<TopTrendyMenu>;
  topTrendyContents: Map<string, number>;

  ordersValidOfTheDay: number = 0;
  ordersWaitingsOfTheDay: number = 0;
  ordersOfLunch: number = 0;
  ordersOfDiner: number = 0;

  numberOfActualTotalMenus: number = 0;
  numberOfActualMixedMenu: number = 0;
  numberOfActualNormalMenu: number = 0;

  numberOfContents: number = 0;
  numberOfEntries: number = 0;
  numberOfDishes: number = 0;
  numberOfGarnishes: number = 0;
  numberOfDairyProducts: number = 0;
  numberOfDesserts: number = 0;

  constructor(
    private statisticsService: StatisticsService,
    private orderService: OrderService,
    private alimentationService: AlimentationService
  ) {}

  ngOnInit(): void {
    this.getNumberOfOrdersPerDay();
    this.getTrendyDiets();
    this.getTrendyContents();
    this.getOrdersInformationsOfTheDay();
  }

  private getNumberOfOrdersPerDay(): void {
    this.statisticsService.getNumberOfOrdersPerDay().subscribe(
      (data) => {
        this.orderPerStatus = data;
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  private getTrendyDiets(): void {
    this.statisticsService.getTrendyDiets().subscribe(
      (data) => {
        this.topTrendyMenus = data;
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  private getTrendyContents(): void {
    this.statisticsService.getTrendyContents().subscribe(
      (data) => {
        this.topTrendyContents = data;
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  private getOrdersInformationsOfTheDay(): void {
    let date: string = moment().format("YYYY-MM-DD");
    let ordersOfToday = this.orderService.getOrdersOfTheDate(date);
    let actualMenus = this.alimentationService.getCurrentsMenus();
    let allContents = this.alimentationService.getAllContents();
    forkJoin([ordersOfToday, actualMenus, allContents]).subscribe(
      (datas) => {
        this.setInfosForOrders(datas[0]);
        this.setInfosForActualMenus(datas[1]);
        this.setInfosForContents(datas[2]);
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  private setInfosForOrders(orders: Order[]): void {
    if (!orders) return;
    this.ordersOfLunch = orders.filter(
      (order) => order.moment === "Déjeuner"
    ).length;
    this.ordersOfDiner = orders.filter(
      (order) => order.moment === "Dîner"
    ).length;
    this.ordersValidOfTheDay = orders.filter(
      (order) => order.orderStatus === Status.VALIDATED
    ).length;
    this.ordersWaitingsOfTheDay = orders.filter(
      (order) => order.orderStatus === Status.WAITING
    ).length;
  }

  private setInfosForActualMenus(menus: Menu[]): void {
    if (!menus) return;
    this.numberOfActualTotalMenus = menus.length;
    this.numberOfActualMixedMenu = menus.filter(
      (menu) => menu.texture == TypeTexture.MIXED
    ).length;
    this.numberOfActualNormalMenu = menus.filter(
      (menu) => menu.texture == TypeTexture.NORMAL
    ).length;
  }

  private setInfosForContents(contents: Content[]): void {
    if (!contents) return;
    this.numberOfContents = contents.length;
    this.numberOfEntries = contents.filter((content) =>
      content.typeMeals.includes("Entrée")
    ).length;
    this.numberOfDishes = contents.filter((content) =>
      content.typeMeals.includes("Plat")
    ).length;
    this.numberOfGarnishes = contents.filter((content) =>
      content.typeMeals.includes("Garniture")
    ).length;
    this.numberOfDairyProducts = contents.filter((content) =>
      content.typeMeals.includes("P.L")
    ).length;
    this.numberOfDesserts = contents.filter((content) =>
      content.typeMeals.includes("Dessert")
    ).length;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error = TypeMessage.NOT_AUTHENTICATED;
    } else {
      this.error = TypeMessage.AN_ERROR_OCCURED;
    }
  }
  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
  }
}
