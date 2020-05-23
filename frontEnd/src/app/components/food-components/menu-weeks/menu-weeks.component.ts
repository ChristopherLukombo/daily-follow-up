import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-menu-weeks",
  templateUrl: "./menu-weeks.component.html",
  styleUrls: ["./menu-weeks.component.scss"],
})
export class MenuWeeksComponent implements OnInit {
  weeks: string[] = ["Semaine 1", "Semaine 2", "Semaine 3", "Semaine 4"];
  selectedButton: number = 0;
  selectedWeek: string;
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

  // TODO : afficher le formulaire en dessous en fonction de la semaine cliqué
  selectWeek(week: string, index: number): void {
    this.selectedButton = index;
    this.selectedWeek = week;
  }
}
