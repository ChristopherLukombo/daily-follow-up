import { Component, OnInit } from "@angular/core";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-clinic-navbar",
  templateUrl: "./clinic-navbar.component.html",
  styleUrls: ["./clinic-navbar.component.scss"],
})
export class ClinicNavbarComponent implements OnInit {
  roomsLogo = faDoorOpen;

  constructor() {}

  ngOnInit(): void {}
}
