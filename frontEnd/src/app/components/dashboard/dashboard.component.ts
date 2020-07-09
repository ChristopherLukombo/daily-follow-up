import { Component, OnInit } from "@angular/core";
import * as moment from "moment";
import { interval, Observable } from "rxjs";
import { distinctUntilChanged, map } from "rxjs/operators";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
})
export class DashboardComponent implements OnInit {
  dateOfTheDay: string;
  hour: Observable<string>;

  constructor() {}

  ngOnInit(): void {
    this.dateOfTheDay = moment().locale("fr").format("dddd Do MMMM YYYY");
    this.hour = interval(1000).pipe(
      map(() => moment().locale("fr").format("LTS"), distinctUntilChanged())
    );
  }
}
