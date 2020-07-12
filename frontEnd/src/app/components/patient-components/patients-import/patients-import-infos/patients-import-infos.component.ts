import { Component, OnInit } from "@angular/core";
import { faFileDownload } from "@fortawesome/free-solid-svg-icons";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-patients-import-infos",
  templateUrl: "./patients-import-infos.component.html",
  styleUrls: ["./patients-import-infos.component.scss"],
})
export class PatientsImportInfosComponent implements OnInit {
  logoDownload = faFileDownload;
  csvCols: Map<number, string> = new Map([
    [1, "Le prénom"],
    [2, "Le nom"],
    [3, "Le sexe (Homme ou Femme)"],
    [4, "La texture des plats"],
    [5, 'Les régimes à suivre, séparés par des ","'],
    [6, 'Les allergies du patient, séparées par des ","'],
    [7, "Le numéro de la chambre"],
  ]);

  constructor() {}

  ngOnInit(): void {}

  downloadTemplate(): void {
    window.open("/assets/patients.csv");
  }
}
