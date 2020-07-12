import { Component, OnInit, Input } from "@angular/core";
import { Day } from "src/app/models/food/day";
import { Content } from "src/app/models/food/content";
import { MomentDay } from "src/app/models/food/moment-day";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-contents-day-menu-lock",
  templateUrl: "./contents-day-menu-lock.component.html",
  styleUrls: ["./contents-day-menu-lock.component.scss"],
})
export class ContentsDayMenuLockComponent implements OnInit {
  @Input() day: Day;
  @Input() momentTime: string;

  contents: Content[] = [];

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.momentTime && this.day) {
      this.loadContents();
    }
  }

  loadContents(): void {
    let moment: MomentDay = this.day.momentDays.find(
      (m) => m.name == this.momentTime
    );
    this.contents.push(moment.entry);
    this.contents.push(moment.dish);
    this.contents.push(moment.garnish);
    if (moment.name !== "Dîner") {
      this.contents.push(moment.dairyProduct);
    }
    this.contents.push(moment.dessert);
  }
}
