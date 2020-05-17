import { Component, OnInit } from "@angular/core";
import { faDrumstickBite } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-food-navbar",
  templateUrl: "./food-navbar.component.html",
  styleUrls: ["./food-navbar.component.scss"],
})
export class FoodNavbarComponent implements OnInit {
  mealAddLogo = faDrumstickBite;

  constructor() {}

  ngOnInit(): void {}
}
