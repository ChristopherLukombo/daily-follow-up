import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import { faAngleDoubleLeft } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-patient-history",
  templateUrl: "./patient-history.component.html",
  styleUrls: ["./patient-history.component.scss"]
})
export class PatientHistoryComponent implements OnInit {
  goBackLogo = faAngleDoubleLeft;

  constructor(private _location: Location) {}

  ngOnInit(): void {}

  goBack(): void {
    this._location.back();
  }
}
