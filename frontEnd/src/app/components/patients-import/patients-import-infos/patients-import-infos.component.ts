import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-patients-import-infos",
  templateUrl: "./patients-import-infos.component.html",
  styleUrls: ["./patients-import-infos.component.scss"],
})
export class PatientsImportInfosComponent implements OnInit {
  csvCols: Map<number, string> = new Map([
    [1, "le nom"],
    [2, "le prénom"],
    [3, "l'email"],
    [4, "la situation familliale"], // retirer ?
    [5, "la date de naissance"], //  sous format : jj/mm/aaa
    [6, "le n° de tél"],
    [7, "le n° de tél mobile"],
    [8, "la situation pro"], // retirer ?
    [9, "le groupe sanguin"],
    [10, "la taille"],
    [11, "le poids"],
    [12, "le sex"], // (écrire Homme ou Femme) check en bdd
    [13, "la texture des plats"], // faire check en bdd
    [14, 'les régimes à suivre, séparés par des ","'], // faire check en bdd
    [15, 'les allergies, séparées par des ","'],
    [16, "la chambre associée au patient"],
    [17, "l'adresse, sous format : rue, ville, code postal"],
  ]);

  constructor() {}

  ngOnInit(): void {}

  downloadTemplate(): void {
    // TODO : download from assets
  }
}
