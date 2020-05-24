import { Component, OnInit } from "@angular/core";
import {
  faStream,
  faClipboard,
  faCarrot,
  faHamburger,
  faClock,
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-food-navbar",
  templateUrl: "./food-navbar.component.html",
  styleUrls: ["./food-navbar.component.scss"],
})
export class FoodNavbarComponent implements OnInit {
  menuPeriodLogo = faStream;
  mealAllLogo = faClipboard;
  mealAddLogo = faCarrot;
  menuAddLogo = faHamburger;
  menuOldLogo = faClock;

  constructor() {}

  ngOnInit(): void {}
}
