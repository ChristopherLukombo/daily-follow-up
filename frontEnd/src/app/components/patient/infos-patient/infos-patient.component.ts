import { Component, OnInit, Input } from "@angular/core";
import { faBed, faUserNurse } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-infos-patient",
  templateUrl: "./infos-patient.component.html",
  styleUrls: ["./infos-patient.component.scss"],
})
export class InfosPatientComponent implements OnInit {
  roomLogo = faBed;
  userLogo = faUserNurse;

  @Input() patient: Patient;

  constructor() {}

  ngOnInit(): void {}
}
