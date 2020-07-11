import { Component, OnInit, Input } from "@angular/core";
import { Order } from "src/app/models/patient/order";
import { Content } from "src/app/models/food/content";

@Component({
  selector: "app-contents-of-today",
  templateUrl: "./contents-of-today.component.html",
  styleUrls: ["./contents-of-today.component.scss"],
})
export class ContentsOfTodayComponent implements OnInit {
  @Input() orders: Order[] = [];
  @Input() moment: string;

  numberOfContents: Map<string, number> = new Map<string, number>();
  totalContents: number = 0;

  constructor() {}

  ngOnInit(): void {
    if (this.moment && this.orders) {
      this.orders
        .filter((order) => order.moment === this.moment)
        .forEach((order) => {
          this.setNumberOfContents(order.entry);
          this.setNumberOfContents(order.dish);
          this.setNumberOfContents(order.garnish);
          if (this.moment !== "Dîner")
            this.setNumberOfContents(order.dairyProduct);
          this.setNumberOfContents(order.dessert);
        });
    }
  }

  setNumberOfContents(content: Content): void {
    if (!content || !content.name) return;
    this.totalContents += 1;
    if (!this.numberOfContents.has(content.name)) {
      this.numberOfContents.set(content.name, 1);
    } else {
      let old: number = this.numberOfContents.get(content.name);
      this.numberOfContents.set(content.name, old + 1);
    }
  }
}
