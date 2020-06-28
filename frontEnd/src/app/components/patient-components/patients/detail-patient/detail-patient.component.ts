import { Component, OnInit, Input } from "@angular/core";
import {
  faAngleDoubleRight,
  faMars,
  faVenus,
} from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-detail-patient",
  templateUrl: "./detail-patient.component.html",
  styleUrls: ["./detail-patient.component.scss"],
})
export class DetailPatientComponent implements OnInit {
  marsLogo = faMars;
  venusLogo = faVenus;
  moreDetailsLogo = faAngleDoubleRight;

  @Input() patient: Patient;

  constructor() {}

  ngOnInit(): void {}
}
