import { Component, OnInit } from "@angular/core";
import { faAddressBook, faUtensils } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-statistics-navbar",
  templateUrl: "./statistics-navbar.component.html",
  styleUrls: ["./statistics-navbar.component.scss"],
})
export class StatisticsNavbarComponent implements OnInit {
  patientLogo = faAddressBook;
  foodLogo = faUtensils;

  constructor() {}

  ngOnInit(): void {}
}
