import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { interval, Observable } from "rxjs";
import { distinctUntilChanged, map } from "rxjs/operators";
import { OrderService } from "src/app/services/order/order.service";
import { Order } from "src/app/models/patient/order";
import { TypeMessage } from "src/app/models/utils/message-enum";
import { Status } from "src/app/models/utils/status-enum";
import { Content } from "src/app/models/food/content";

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

  entries: string[] = [];
  dishes: string[] = [];
  garnishes: string[] = [];
  dairyProducts: string[] = [];
  desserts: string[] = [];

  loading: boolean = false;
  warning: string;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.initDateAnHourOfTheDay();
    this.loading = true;
    let date: string = moment().format("YYYY-MM-DD");
    this.orderService.getOrdersOfTheWeekByDate(date).subscribe(
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

  onDownloadCoupons(): void {}
}
