import { Component, OnInit } from "@angular/core";
import { faAngleDoubleRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-detail-patient",
  templateUrl: "./detail-patient.component.html",
  styleUrls: ["./detail-patient.component.scss"]
})
export class DetailPatientComponent implements OnInit {
  moreDetailsLogo = faAngleDoubleRight;
  constructor() {}

  ngOnInit(): void {}
}
