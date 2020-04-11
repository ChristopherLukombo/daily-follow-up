import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import {
  faUserEdit,
  faUserSlash,
  faClock,
  faUserPlus,
  faAngleDoubleLeft,
} from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";
import { ActivatedRoute } from "@angular/router";
import { PatientService } from "src/app/services/patient/patient.service";

@Component({
  selector: "app-patient",
  templateUrl: "./patient.component.html",
  styleUrls: ["./patient.component.scss"],
})
export class PatientComponent implements OnInit {
  editLogo = faUserEdit;
  deleteLogo = faUserSlash;
  historyLogo = faClock;
  addLogo = faUserPlus;
  goBackLogo = faAngleDoubleLeft;

  patient: Patient;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private _location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.forEach((params) => {
      this.patientService.getPatient(params["id"]).subscribe(
        (data) => {
          this.patient = data;
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    });
  }

  goBack(): void {
    this._location.back();
  }
}
