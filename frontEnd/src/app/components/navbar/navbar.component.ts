import { Component, OnInit } from "@angular/core";
import {
  faAddressBook,
  faUtensils,
  faChartPie,
  faClinicMedical
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.scss"]
})
export class NavbarComponent implements OnInit {
  patientLogo = faAddressBook;
  foodLogo = faUtensils;
  statsLogo = faChartPie;
  clinicLogo = faClinicMedical;

  constructor() {}

  ngOnInit(): void {}
}
