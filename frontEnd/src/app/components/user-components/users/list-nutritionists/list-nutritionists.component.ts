import { Component, OnInit, Input } from "@angular/core";
import { User } from "src/app/models/user/user";
import { faInfoCircle } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-list-nutritionists",
  templateUrl: "./list-nutritionists.component.html",
  styleUrls: ["./list-nutritionists.component.scss"],
})
export class ListNutritionistsComponent implements OnInit {
  detailLogo = faInfoCircle;
  page: number = 1;
  pagination = { itemsPerPage: 8, currentPage: this.page };

  lastName: string;
  firstName: string;

  selectedNutritionist: User;

  @Input() nutritionists: User[] = [];

  constructor() {}

  ngOnInit(): void {}

  pageChanged(event) {
    this.pagination.currentPage = event;
  }

  selectNutritionist(nutritionist: User): void {
    this.selectedNutritionist = nutritionist;
  }

  removeFromList(id: number): void {
    this.nutritionists = this.nutritionists.filter((n) => n.id !== id);
  }
}
