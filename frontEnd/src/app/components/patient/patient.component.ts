import { Component, OnInit } from "@angular/core";
import {
  faUserEdit,
  faUserSlash,
  faClock,
  faRecycle,
} from "@fortawesome/free-solid-svg-icons";
import { Patient } from "src/app/models/patient/patient";
import { ActivatedRoute } from "@angular/router";
import { PatientService } from "src/app/services/patient/patient.service";

@Component({
  selector: "app-patient",
  templateUrl: "./patient.component.html",
  styleUrls: ["./patient.component.scss"],
})
export class PatientComponent implements OnInit {
  editLogo = faUserEdit;
  deleteLogo = faUserSlash;
  historyLogo = faClock;
  restoreLogo = faRecycle;

  patient: Patient;
  isActive: boolean;

  error: string;
  warning: string;
  loading: Boolean = false;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.forEach((params) => {
      this.patientService.getPatient(parseInt(params["id"])).subscribe(
        (data) => {
          this.loading = false;
          if (data) {
            this.patient = data;
            this.isActive = this.patient.state;
          } else {
            this.patientDoesNotExist();
          }
        },
        (error) => {
          this.loading = false;
          this.catchError(error);
        }
      );
    });
  }

  patientDoesNotExist(): void {
    this.warning = "Ce patient n'éxiste pas. Veuillez réessayer.";
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
