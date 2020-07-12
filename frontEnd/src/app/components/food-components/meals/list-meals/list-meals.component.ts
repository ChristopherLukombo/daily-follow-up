import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Content } from "src/app/models/food/content";
import {
  faTimesCircle,
  faCheckCircle,
} from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
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
  filter: boolean[] = [true, true, true, true, true];
  filteredContents: Content[] = [];

  page: number = 1;
  pagination = { itemsPerPage: 8, currentPage: this.page };

  selectedRow: number;

  @Output() selectedContent = new EventEmitter<Content>();

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.contents) this.filteredContents = this.contents;
  }

  filterChange(): void {
    this.filteredContents = this.contents.filter((c) => {
      return (
        this.filterByTypes(c, this.typeMeals[0], 0) ||
        this.filterByTypes(c, this.typeMeals[1], 1) ||
        this.filterByTypes(c, this.typeMeals[2], 2) ||
        this.filterByTypes(c, this.typeMeals[3], 3) ||
        this.filterByTypes(c, this.typeMeals[4], 4)
      );
    });
  }

  filterByTypes(content: Content, type: string, indexFilter: number): boolean {
    return content.typeMeals.includes(type) && this.filter[indexFilter];
  }

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectContent(content: Content, index: number): void {
    this.selectedRow = index;
    this.selectedContent.emit(content);
  }
}
