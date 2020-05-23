import { Component, OnInit } from "@angular/core";
import {
  faStream,
  faCarrot,
  faHamburger,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-food-navbar",
  templateUrl: "./food-navbar.component.html",
  styleUrls: ["./food-navbar.component.scss"],
})
export class FoodNavbarComponent implements OnInit {
  menuPeriodLogo = faStream;
  mealAddLogo = faCarrot;
  menuAddLogo = faHamburger;

  constructor() {}

  ngOnInit(): void {}
}
