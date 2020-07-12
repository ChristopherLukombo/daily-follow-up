import { Component, OnInit } from "@angular/core";
import {
  faCalendarAlt,
  faClipboard,
  faHamburger,
  faClock,
  faPlus,
  faCapsules,
  faCopy,
} from "@fortawesome/free-solid-svg-icons";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-food-navbar",
  templateUrl: "./food-navbar.component.html",
  styleUrls: ["./food-navbar.component.scss"],
})
export class FoodNavbarComponent implements OnInit {
  menuPeriodLogo = faCalendarAlt;
  mealAllLogo = faClipboard;
  mealAddLogo = faPlus;
  menuAddLogo = faHamburger;
  menuDeclineLogo = faCopy;
  dietsLogo = faCapsules;
  menuAllLogo = faClock;

  isCaregiver: boolean = false;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.isCaregiver = this.loginService.isCaregiver();
  }
}
