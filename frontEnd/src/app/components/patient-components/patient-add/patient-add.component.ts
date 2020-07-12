import { Component, OnInit } from "@angular/core";
import { Diet } from "src/app/models/patient/diet";
import { Texture } from "src/app/models/food/texture";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { forkJoin } from "rxjs";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-patient-add",
  templateUrl: "./patient-add.component.html",
  styleUrls: ["./patient-add.component.scss"],
})
export class PatientAddComponent implements OnInit {
  dietsAvailable: Diet[] = [];
  texturesAvailable: Texture[] = [];

  loading: boolean = false;
  error: string;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    let allDiets = this.alimentationService.getAllDiets();
    let allTextures = this.alimentationService.getAllTextures();
    forkJoin([allDiets, allTextures]).subscribe(
      (datas) => {
        this.dietsAvailable = datas[0];
        this.texturesAvailable = datas[1];

        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
