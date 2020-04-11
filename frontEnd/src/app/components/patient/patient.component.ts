import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import {
  faUserEdit,
  faUserSlash,
  faClock,
  faUserPlus,
  faAngleDoubleLeft,
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
  addLogo = faUserPlus;
  goBackLogo = faAngleDoubleLeft;

  patient: Patient;

  error: string;
  warningTitle: string;
  warning: string;
  loading: Boolean = false;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private _location: Location
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.forEach((params) => {
      this.patientService.getPatient(parseInt(params["id"])).subscribe(
        (data) => {
          this.loading = false;
          data == null ? this.patientDoesNotExist() : (this.patient = data);
        },
        (error) => {
          this.loading = false;
          this.catchError(error);
        }
      );
    });
  }

  goBack(): void {
    this._location.back();
  }

  patientDoesNotExist(): void {
    this.warningTitle = "Désolé";
    this.warning = "Ce patient n'éxiste pas, veuillez réessayer.";
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
