import { Component, OnInit, Input } from "@angular/core";
import { faMars } from "@fortawesome/free-solid-svg-icons";
import { faVenus } from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-list-patients",
  templateUrl: "./list-patients.component.html",
  styleUrls: ["./list-patients.component.scss"],
})
export class ListPatientsComponent implements OnInit {
  marsLogo = faMars;
  venusLogo = faVenus;

  @Input() patients: Patient[] = [];

  constructor() {}

  ngOnInit(): void {}
}
