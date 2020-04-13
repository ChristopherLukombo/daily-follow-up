import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
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

  page: number = 1;
  lastName: string;
  firstName: string;

  @Input() patients: Patient[] = [];
  @Output() selectedPatient = new EventEmitter<Patient>();
  selectedRow: number;

  constructor() {}

  ngOnInit(): void {}

  selectPatient(patient: Patient, index: number): void {
    this.selectedRow = index;
    this.selectedPatient.emit(patient);
  }
}
