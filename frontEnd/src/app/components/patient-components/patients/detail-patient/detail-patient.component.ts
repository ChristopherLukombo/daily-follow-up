import { Component, OnInit, Input } from "@angular/core";
import { faAngleDoubleRight } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-detail-patient",
  templateUrl: "./detail-patient.component.html",
  styleUrls: ["./detail-patient.component.scss"],
})
export class DetailPatientComponent implements OnInit {
  moreDetailsLogo = faAngleDoubleRight;

  @Input() patient: Patient;

  constructor() {}

  ngOnInit(): void {}
}
