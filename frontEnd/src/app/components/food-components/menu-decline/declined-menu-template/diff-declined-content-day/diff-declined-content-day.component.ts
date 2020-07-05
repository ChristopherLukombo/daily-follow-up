import { Component, OnInit, Input } from "@angular/core";
import { MomentDay } from "src/app/models/food/moment-day";
import { Content } from "src/app/models/food/content";
import { Day } from "src/app/models/food/day";

@Component({
  selector: "app-diff-declined-content-day",
  templateUrl: "./diff-declined-content-day.component.html",
  styleUrls: ["./diff-declined-content-day.component.scss"],
})
export class DiffDeclinedContentDayComponent implements OnInit {
  @Input() from: Day;
  @Input() to: Day;
  @Input() moment: string;

  baseContents: Content[];
  newContents: Content[];

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.from && this.to && this.moment) {
      this.baseContents = this.getContents(this.from);
      this.newContents = this.getContents(this.to);
    }
  }

  /**
   * Retourne tout les plats d'un moment de la journée (dejeuner/diner)
   * @param moment
   * @returns la liste de Content
   */
  getContents(day: Day): Content[] {
    let momentDay: MomentDay = day.momentDays.find(
      (m) => m.name == this.moment
    );
    let contents: Content[] = [];
    contents.push(momentDay.entry);
    contents.push(momentDay.dish);
    contents.push(momentDay.garnish);
    if (momentDay.name !== "Dîner") {
      contents.push(momentDay.dairyProduct);
    }
    contents.push(momentDay.dessert);
    return contents;
  }
}
