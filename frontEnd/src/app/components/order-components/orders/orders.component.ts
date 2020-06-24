import { Component, OnInit } from "@angular/core";
import { Order } from "src/app/models/patient/order";
import * as moment from "moment";

@Component({
  selector: "app-orders",
  templateUrl: "./orders.component.html",
  styleUrls: ["./orders.component.scss"],
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];

  dateOfTheDay: string;
  daysOfTheWeek: string[] = [
    "22/06",
    "23/06",
    "24/06",
    "25/06",
    "26/06",
    "27/06",
    "28/06",
  ];
  moments: string[] = ["Déjeuner", "Dîner"];

  selectedDay: string;
  selectedMoment: string;

  loading: boolean = false;
  error: string;

  constructor() {}

  // TODO : récup orders correctement + gérer loader + error dans html
  ngOnInit(): void {
    moment.locale();
    this.dateOfTheDay = moment().locale("fr").format("dddd Do MMMM YYYY");
  }

  selectDay(day: string): void {
    this.selectedDay = day;
  }

  selectMoment(moment: string): void {
    this.selectedMoment = moment;
  }
}
