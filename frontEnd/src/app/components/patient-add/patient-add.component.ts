import { Component, OnInit } from "@angular/core";
import {
  faAngleDoubleLeft,
  faPlus,
  faMinus
} from "@fortawesome/free-solid-svg-icons";
import { Diet } from "src/app/models/diet";
import { Texture } from "src/app/models/texture";

@Component({
  selector: "app-patient-add",
  templateUrl: "./patient-add.component.html",
  styleUrls: ["./patient-add.component.scss"]
})
export class PatientAddComponent implements OnInit {
  goBackLogo = faAngleDoubleLeft;
  addLogo = faPlus;
  removeLogo = faMinus;

  allergy: string;
  allergies: string[] = [];

  dietsAvailable: Array<Diet> = [
    { id: 1, name: "Normale" },
    { id: 2, name: "Sans porc" },
    { id: 3, name: "Hyperprotéiné" },
    { id: 4, name: "Sans sel" },
    { id: 5, name: "Sans résidu" },
    { id: 6, name: "Diabétique" }
  ];
  texturesAvailable: Array<Texture> = [
    { id: 1, name: "Normale" },
    { id: 2, name: "Haché" },
    { id: 3, name: "Mixé" }
  ];

  constructor() {}

  ngOnInit(): void {}

  addAllergy(allergy: string): void {
    if (allergy != undefined) {
      this.allergies.push(allergy);
    }
  }

  deleteAllergy(allergy: string): void {
    let i = this.allergies.indexOf(allergy);
    this.allergies.splice(i, 1);
  }
}
