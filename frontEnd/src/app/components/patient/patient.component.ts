import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import {
  faUserEdit,
  faUserSlash,
  faClock,
  faUserPlus,
  faAngleDoubleLeft
} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-patient",
  templateUrl: "./patient.component.html",
  styleUrls: ["./patient.component.scss"]
})
export class PatientComponent implements OnInit {
  editLogo = faUserEdit;
  deleteLogo = faUserSlash;
  historyLogo = faClock;
  addLogo = faUserPlus;
  goBackLogo = faAngleDoubleLeft;

  constructor(private _location: Location) {}

  ngOnInit(): void {}

  goBack(): void {
    this._location.back();
  }
}
