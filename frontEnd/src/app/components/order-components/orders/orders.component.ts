import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-orders",
  templateUrl: "./orders.component.html",
  styleUrls: ["./orders.component.scss"],
})
export class OrdersComponent implements OnInit {
  actualWeek: number;
  actualYear: number;
  specificWeek: string;

  headerTable: string;
  daysOfTheWeek: Map<string, string> = new Map([]);
  moments: string[] = ["Déjeuner", "Dîner"];

  selectedDate: string;
  selectedMoment: string;

  isCaregiver: boolean = false;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.isCaregiver = this.loginService.isCaregiver();
    this.goCurrentWeek();
    this.selectDayOfToday();
    this.selectMoment(this.moments[0]);
  }

  selectDayOfToday(): void {
    let dateOfTheDay: string = moment().locale("fr").format("dddd");
    this.selectDate(this.daysOfTheWeek.get(dateOfTheDay));
  }

  selectDate(date: string): void {
    this.selectedDate = date;
  }

  selectMoment(moment: string): void {
    this.selectedMoment = moment;
  }

  goCurrentWeek(): void {
    this.actualWeek = moment().locale("fr").week();
    this.actualYear = moment().locale("fr").year();
    this.generateTableWeek(this.actualWeek, this.actualYear);
  }

  goWeek(): void {
    if (!this.specificWeek || this.specificWeek.startsWith("0")) return;
    this.actualWeek = parseInt(
      this.specificWeek.split("-")[1].replace("W", "")
    );
    this.actualYear = parseInt(this.specificWeek.split("-")[0]);
    this.generateTableWeek(this.actualWeek, this.actualYear);
  }

  generateTableWeek(week: number, year): void {
    let monday = moment().locale("fr").year(year).week(week).startOf("isoWeek");
    for (let i = 0; i < 7; i++) {
      let day = moment(monday).add(i, "days");
      this.daysOfTheWeek.set(
        day.locale("fr").format("dddd"),
        day.format("YYYY-MM-DD")
      );
    }
    this.selectDate(this.daysOfTheWeek.get("lundi"));
  }

  /**
   * Obligatoire pour pas que la pipe
   * keyvalue casse l'ordre des clés
   */
  notOrdered = () => {
    return 0;
  };
}
