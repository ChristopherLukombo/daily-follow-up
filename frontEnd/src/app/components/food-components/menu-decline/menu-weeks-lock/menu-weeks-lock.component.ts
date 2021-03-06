import { Component, OnInit, Input } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import * as moment from "moment";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-menu-weeks-lock",
  templateUrl: "./menu-weeks-lock.component.html",
  styleUrls: ["./menu-weeks-lock.component.scss"],
})
export class MenuWeeksLockComponent implements OnInit {
  @Input() menu: Menu;

  selectedWeek: number;
  moments: string[] = ["Déjeuner", "Dîner"];
  indexOfDays: Map<string, number> = new Map([
    ["Lundi", 0],
    ["Mardi", 1],
    ["Mercredi", 2],
    ["Jeudi", 3],
    ["Vendredi", 4],
    ["Samedi", 5],
    ["Dimanche", 6],
  ]);

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.menu) this.selectedWeek = this.menu.weeks[0].number;
  }

  selectWeek(numWeek: number): void {
    this.selectedWeek = numWeek;
  }
}
