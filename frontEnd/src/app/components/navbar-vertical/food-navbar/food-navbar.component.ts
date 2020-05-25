import { Component, OnInit } from "@angular/core";
import {
  faStream,
  faClipboard,
  faHamburger,
  faClock,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-food-navbar",
  templateUrl: "./food-navbar.component.html",
  styleUrls: ["./food-navbar.component.scss"],
})
export class FoodNavbarComponent implements OnInit {
  menuPeriodLogo = faStream;
  mealAllLogo = faClipboard;
  mealAddLogo = faPlus;
  menuAddLogo = faHamburger;
  menuOldLogo = faClock;

  constructor() {}

  ngOnInit(): void {}
}
