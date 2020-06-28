import { Component, OnInit } from "@angular/core";
import { Order } from "src/app/models/patient/order";
import * as moment from "moment";
import { stringify } from "querystring";

@Component({
  selector: "app-orders",
  templateUrl: "./orders.component.html",
  styleUrls: ["./orders.component.scss"],
})
export class OrdersComponent implements OnInit {
  orders: Order[] = [];

  daysOfTheWeek: Map<string, string> = new Map([
    ["Lundi", null],
    ["Mardi", null],
    ["Mercredi", null],
    ["Jeudi", null],
    ["Vendredi", null],
    ["Samedi", null],
    ["Dimanche", null],
  ]);
  moments: string[] = ["Déjeuner", "Dîner"];

  selectedDay: string;
  selectedMoment: string;

  loading: boolean = false;
  error: string;

  constructor() {}

  // TODO : récup orders correctement + gérer loader + error dans html
  ngOnInit(): void {
    this.initActualWeek();
    this.selectDayOfToday();
    this.selectMoment(this.moments[0]);
  }

  initActualWeek(): void {
    let startOfTheWeek = moment().startOf("isoWeek");
    let days: string[] = Array.from(this.daysOfTheWeek.keys());
    for (let i = 0; i < 7; i++) {
      this.daysOfTheWeek.set(
        days[i],
        moment(startOfTheWeek).add(i, "days").format("DD/MM")
      );
    }
  }

  selectDayOfToday(): void {
    let dateOfTheDay: string = moment().locale("fr").format("dddd");
    // Première lettre en majuscule pour récupèrer la date
    let index: string =
      dateOfTheDay.charAt(0).toUpperCase() + dateOfTheDay.slice(1);
    this.selectDay(this.daysOfTheWeek.get(index));
  }

  selectDay(day: string): void {
    this.selectedDay = day;
  }

  selectMoment(moment: string): void {
    this.selectedMoment = moment;
  }

  /**
   * Obligatoire pour pas que la pipe
   * keyvalue casse l'ordre des clés
   */
  notOrdered = () => {
    return 0;
  };
}
