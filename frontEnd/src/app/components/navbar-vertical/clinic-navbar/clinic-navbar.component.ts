import { Component, OnInit } from "@angular/core";
import { faDoorOpen, faChartPie } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-clinic-navbar",
  templateUrl: "./clinic-navbar.component.html",
  styleUrls: ["./clinic-navbar.component.scss"],
})
export class ClinicNavbarComponent implements OnInit {
  roomsLogo = faDoorOpen;
  patientLogo = faChartPie;
  foodLogo = faChartPie;

  constructor() {}

  ngOnInit(): void {}
}
