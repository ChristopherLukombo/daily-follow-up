import { Component, OnInit } from "@angular/core";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";
import { PatientService } from "src/app/services/patient/patient.service";

@Component({
  selector: "app-patients",
  templateUrl: "./patients.component.html",
  styleUrls: ["./patients.component.scss"],
})
export class PatientsComponent implements OnInit {
  addLogo = faUserPlus;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.patientService.getAllPatients().subscribe(
      (data) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
