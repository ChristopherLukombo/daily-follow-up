import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import {
  faTimesCircle,
  faCheckCircle,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-list-meals",
  templateUrl: "./list-meals.component.html",
  styleUrls: ["./list-meals.component.scss"],
})
export class ListMealsComponent implements OnInit {
  trueLogo = faCheckCircle;
  falseLogo = faTimesCircle;

  @Input() contents: Content[] = [];
  typeMeals: string[] = ["Entrée", "Plat", "Garniture", "P.L", "Dessert"];
  name: string;

  page: number = 1;
  pagination = { itemsPerPage: 8, currentPage: this.page };

  selectedRow: number;

  constructor() {}

  ngOnInit(): void {}

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectContent(content: Content, index: number): void {
    this.selectedRow = index;
    console.log(content);
  }
}
