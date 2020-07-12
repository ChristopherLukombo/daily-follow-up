import { Component, OnInit } from "@angular/core";
import { faDoorOpen, faPlus } from "@fortawesome/free-solid-svg-icons";

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
  FloorAddLogo = faPlus;

  constructor() {}

  ngOnInit(): void {}
}
