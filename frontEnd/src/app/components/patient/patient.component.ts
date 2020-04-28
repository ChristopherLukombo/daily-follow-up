import { Component, OnInit } from "@angular/core";
import {
  faUserEdit,
  faClock,
  faRecycle,
  faLock,
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
  lockLogo = faLock;
  editLogo = faUserEdit;
  historyLogo = faClock;
  restoreLogo = faRecycle;

  patient: Patient;

  btnDelete: string = "Supprimer le patient";
  confirmDelete: string =
    "Une fois supprimé, le patient se retrouvera dans la liste des anciens patients.";

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

  deletePatient(): void {
    this.route.queryParams.forEach((params) => {
      this.patientService.deletePatient(parseInt(params["id"])).subscribe(
        () => {
          this.patient.state = false;
          this.patient.roomId = null;
          this.patient = Object.assign({}, this.patient);
          console.log("notifier success");
        },
        (error) => {
          console.log("notifier error");
        }
      );
    });
  }

  restorePatient(): void {
    this.route.queryParams.forEach((params) => {
      this.patientService.restorePatient(parseInt(params["id"])).subscribe(
        () => {
          this.patient.state = true;
          this.patient = Object.assign({}, this.patient);
          console.log("notifier success");
        },
        (error) => {
          console.log("notifier error");
        }
      );
    });
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
