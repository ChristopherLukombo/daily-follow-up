import { Component, OnInit } from "@angular/core";
import {
  faAddressBook,
  faUtensils,
  faChartPie,
  faClinicMedical,
  faClock,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { Router } from "@angular/router";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.scss"],
})
export class NavbarComponent implements OnInit {
  patientLogo = faAddressBook;
  foodLogo = faUtensils;
  statsLogo = faChartPie;
  clinicLogo = faClinicMedical;
  historyLogo = faClock;
  logInLogo = faUser;
  /***/
  token: string = localStorage.getItem("token");

  constructor() {}

  ngOnInit(): void {}
}
