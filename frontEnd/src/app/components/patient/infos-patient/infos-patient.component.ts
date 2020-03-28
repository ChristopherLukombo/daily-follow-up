import { Component, OnInit } from "@angular/core";
import { faBed, faUserNurse } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-infos-patient",
  templateUrl: "./infos-patient.component.html",
  styleUrls: ["./infos-patient.component.scss"]
})
export class InfosPatientComponent implements OnInit {
  roomLogo = faBed;
  userLogo = faUserNurse;

  constructor() {}

  ngOnInit(): void {}
}
