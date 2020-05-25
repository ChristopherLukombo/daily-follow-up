import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-menu-weeks",
  templateUrl: "./menu-weeks.component.html",
  styleUrls: ["./menu-weeks.component.scss"],
})
export class MenuWeeksComponent implements OnInit {
  weeks: number[] = [1, 2, 3, 4];
  selectedWeek: number = this.weeks[0];
  numWeek: number = 3;
  days: string[] = [
    "Lundi",
    "Mardi",
    "Mercredi",
    "Jeudi",
    "Vendredi",
    "Samedi",
    "Dimanche",
  ];

  constructor() {}

  ngOnInit(): void {}

  selectWeek(numWeek: number): void {
    this.selectedWeek = numWeek;
  }
}
