import { Component, OnInit } from "@angular/core";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-patients",
  templateUrl: "./patients.component.html",
  styleUrls: ["./patients.component.scss"],
})
export class PatientsComponent implements OnInit {
  patients: Patient[] = [];
  patient: Patient;

  error: string;
  loading: Boolean = false;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.loading = true;
    this.patientService.getAllPatients().subscribe(
      (data) => {
        this.loading = false;
        this.patients = data;
      },
      (error) => {
        this.loading = false;
        this.catchError(error);
      }
    );
  }

  setPatient(patient: Patient): void {
    this.patient = patient;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error = TypeMessage.NOT_AUTHENTICATED;
    } else {
      this.error = TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
