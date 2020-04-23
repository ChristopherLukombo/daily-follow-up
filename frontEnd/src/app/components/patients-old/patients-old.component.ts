import { Component, OnInit } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";
import { PatientService } from "src/app/services/patient/patient.service";

@Component({
  selector: "app-patients-old",
  templateUrl: "./patients-old.component.html",
  styleUrls: ["./patients-old.component.scss"],
})
export class PatientsOldComponent implements OnInit {
  patients: Patient[] = [];
  patient: Patient;

  error: string;
  loading: Boolean = false;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.loading = true;
    this.patientService.getAllFormerPatients().subscribe(
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
    if (error && error === 403) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
