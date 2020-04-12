import { Component, OnInit } from "@angular/core";
import { faUserPlus, faUserFriends } from "@fortawesome/free-solid-svg-icons";
import { PatientService } from "src/app/services/patient/patient.service";
import { Patient } from "src/app/models/patient/patient";

@Component({
  selector: "app-patients",
  templateUrl: "./patients.component.html",
  styleUrls: ["./patients.component.scss"],
})
export class PatientsComponent implements OnInit {
  addLogo = faUserPlus;
  addManyLogo = faUserFriends;

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
    if (error != undefined && error == 403) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
