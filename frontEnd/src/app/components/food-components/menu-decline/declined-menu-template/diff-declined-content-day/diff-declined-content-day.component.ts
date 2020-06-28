import { Component, OnInit, Input } from "@angular/core";
import { MomentDay } from "src/app/models/food/moment-day";
import { Content } from "src/app/models/food/content";

@Component({
  selector: "app-diff-declined-content-day",
  templateUrl: "./diff-declined-content-day.component.html",
  styleUrls: ["./diff-declined-content-day.component.scss"],
})
export class DiffDeclinedContentDayComponent implements OnInit {
  @Input() from: MomentDay;
  @Input() to: MomentDay;

  baseContents: Content[];
  newContents: Content[];

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.from && this.to) {
      this.baseContents = this.getContents(this.from);
      this.newContents = this.getContents(this.to);
    }
  }

  /**
   * Retourne tout les plats d'un moment de la journée (dejeuner/diner)
   * @param moment
   * @returns la liste de Content
   */
  getContents(moment: MomentDay): Content[] {
    let contents: Content[] = [];
    contents.push(moment.entry);
    contents.push(moment.dish);
    contents.push(moment.garnish);
    if (moment.name !== "Dîner") {
      contents.push(moment.dairyProduct);
    }
    contents.push(moment.dessert);
    return contents;
  }
}
