import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { interval, Observable } from "rxjs";
import { distinctUntilChanged, map } from "rxjs/operators";
import { OrderService } from "src/app/services/order/order.service";
import { Order } from "src/app/models/patient/order";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { Status } from "src/app/models/utils/status-enum";
import { FileService } from "src/app/services/file/file.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
})
export class DashboardComponent implements OnInit {
  dateOfTheDay: string;
  hour: Observable<string>;

  orders: Order[] = [];
  moments: string[] = ["Déjeuner", "Dîner"];
  actualMoment: string;

  entries: string[] = [];
  dishes: string[] = [];
  garnishes: string[] = [];
  dairyProducts: string[] = [];
  desserts: string[] = [];

  loading: boolean = false;
  warning: string;
  downloading: boolean = false;

  constructor(
    private orderService: OrderService,
    private fileService: FileService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.initDateAnHourOfTheDay();
    this.loading = true;
    let date: string = moment().format("YYYY-MM-DD");
    this.orderService.getOrdersOfTheDate(date).subscribe(
      (data) => {
        if (data) {
          this.orders = data.filter(
            (order) => order.orderStatus === Status.VALIDATED
          );
          this.loadAllTypesMeals(this.orders);
          this.removeAllDuplicates();
        } else {
          this.warning = TypeMessage.NO_ORDERS_OF_TODAY_YET;
        }
        this.loading = false;
      },
      (error) => {
        this.warning = TypeMessage.ORDERS_OF_TODAY_ARE_NOT_AVAILABLE;
        this.loading = false;
      }
    );
  }

  initDateAnHourOfTheDay(): void {
    this.actualMoment = this.getMomentOfTheDay();
    this.dateOfTheDay = moment().locale("fr").format("dddd Do MMMM YYYY");
    this.hour = interval(1000).pipe(
      map(() => moment().locale("fr").format("LTS"), distinctUntilChanged())
    );
  }

  loadAllTypesMeals(orders: Order[]): void {
    if (!orders || !orders.length) return;
    orders.forEach((order) => {
      this.entries.push(order.entry.name);
      this.dishes.push(order.dish.name);
      this.garnishes.push(order.garnish.name);
      if (order.moment !== "Dîner")
        this.dairyProducts.push(order.dairyProduct.name);
      this.desserts.push(order.dessert.name);
    });
  }

  removeAllDuplicates(): void {
    this.entries = this.removeDuplicate(this.entries);
    this.dishes = this.removeDuplicate(this.dishes);
    this.garnishes = this.removeDuplicate(this.garnishes);
    this.dairyProducts = this.removeDuplicate(this.dairyProducts);
    this.desserts = this.removeDuplicate(this.desserts);
  }

  removeDuplicate(contents: string[]): string[] {
    return Array.from(new Set(contents));
  }

  onDownloadCoupons(): void {
    if (!this.actualMoment) return;
    let date: string = moment().format("YYYY-MM-DD");
    this.downloading = true;
    this.fileService.getCouponsOfTheDate(date, this.actualMoment).subscribe(
      (data) => {
        let blob = new Blob([data], { type: "application/pdf" });
        let url = window.URL.createObjectURL(blob);
        let link = document.createElement("a");
        link.href = url;
        link.download = "coupons_" + this.actualMoment + "_" + date + ".pdf";
        link.dispatchEvent(
          new MouseEvent("click", {
            bubbles: true,
            cancelable: true,
            view: window,
          })
        );
        setTimeout(function () {
          window.URL.revokeObjectURL(url);
          link.remove();
        }, 100);
        this.downloading = false;
      },
      (error) => {
        this.toastrService.error(this.getError(error), "Oops !");
        this.downloading = false;
      }
    );
  }

  getMomentOfTheDay(): string {
    let now = moment();
    let deadline = now.clone().hour(12).minute(0);
    return now.isBefore(deadline) ? this.moments[0] : this.moments[1];
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
