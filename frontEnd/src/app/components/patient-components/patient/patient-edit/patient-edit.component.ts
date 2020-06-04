import { Component, OnInit } from "@angular/core";
import { Patient } from "src/app/models/patient/patient";
import { ActivatedRoute } from "@angular/router";
import { PatientService } from "src/app/services/patient/patient.service";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { forkJoin } from "rxjs";
import { Diet } from "src/app/models/patient/diet";
import { Texture } from "src/app/models/food/texture";

@Component({
  selector: "app-patient-edit",
  templateUrl: "./patient-edit.component.html",
  styleUrls: ["./patient-edit.component.scss"],
})
export class PatientEditComponent implements OnInit {
  patient: Patient;
  dietsAvailable: Diet[] = [];
  texturesAvailable: Texture[] = [];

  error: string;
  warning: string;
  loading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private alimentationService: AlimentationService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.forEach((params) => {
      let patient = this.patientService.getPatient(parseInt(params["id"]));
      let allDiets = this.alimentationService.getAllDiets();
      let allTextures = this.alimentationService.getAllTextures();
      forkJoin([patient, allDiets, allTextures]).subscribe(
        (datas) => {
          datas[0] && datas[0].state
            ? (this.patient = datas[0])
            : this.patientDoesNotExist();
          this.dietsAvailable = datas[1];
          this.texturesAvailable = datas[2];
          this.loading = false;
        },
        (error) => {
          this.catchError(error);
          this.loading = false;
        }
      );
    });
  }

  patientDoesNotExist(): void {
    this.warning =
      "Ce patient n'existe pas, ou ne fait plus partie de la clinique. Veuillez réessayer.";
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
