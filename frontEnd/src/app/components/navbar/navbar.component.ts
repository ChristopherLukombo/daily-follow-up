import { Component, OnInit } from "@angular/core";
import {
  faAddressBook,
  faUtensils,
  faClipboardList,
  faChartPie,
  faUserAlt,
  faClinicMedical,
  faClock,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { LoginService } from "src/app/services/login/login.service";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.scss"],
})
export class NavbarComponent implements OnInit {
  patientLogo = faAddressBook;
  foodLogo = faUtensils;
  orderLogo = faClipboardList;
  userLogo = faUserAlt;
  clinicLogo = faClinicMedical;
  historyLogo = faClock;
  logInLogo = faUser;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {}

  /**
   * Check si l'utilisateur est connecté à l'application
   */
  connected(): Boolean {
    return (
      this.loginService.isAuthenticated() &&
      !this.loginService.isTokenExpired() &&
      this.loginService.hasChangedPassword()
    );
  }
}
