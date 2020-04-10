import { Component, OnInit } from "@angular/core";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-patients",
  templateUrl: "./patients.component.html",
  styleUrls: ["./patients.component.scss"],
})
export class PatientsComponent implements OnInit {
  addLogo = faUserPlus;

  patients: Patient[] = [];
  patient: Patient;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.patientService.getAllPatients().subscribe(
      (data) => {
        this.patients = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  setPatient(patient: Patient): void {
    this.patient = patient;
  }
}
