import { Component, OnInit } from "@angular/core";
import { faClock, faPlus } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-history-patient",
  templateUrl: "./history-patient.component.html",
  styleUrls: ["./history-patient.component.scss"]
})
export class HistoryPatientComponent implements OnInit {
  circleLogo = faClock;
  moreLogo = faPlus;

  constructor() {}

  ngOnInit(): void {}
}
