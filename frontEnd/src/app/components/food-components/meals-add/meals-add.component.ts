import { Component, OnInit } from "@angular/core";
import { faPlus, faRedo } from "@fortawesome/free-solid-svg-icons";
import { ContentDTO } from "src/app/models/dto/food/contentDTO";

@Component({
  selector: "app-meals-add",
  templateUrl: "./meals-add.component.html",
  styleUrls: ["./meals-add.component.scss"],
})
export class MealsAddComponent implements OnInit {
  moreLogo = faPlus;
  revertLogo = faRedo;

  nbForms: number[] = [0];
  mealsToCreate: ContentDTO[] = [];

  constructor() {}

  ngOnInit(): void {}

  createForm(): void {
    this.nbForms.push(0);
  }

  deleteForm(i: number): void {
    // TODO : Supprimer l'index en question (et pas juste dernier élement)
    const index = this.nbForms.indexOf(i);
    this.nbForms.slice(index, 1);
    console.log(this.nbForms.length);
  }

  onCreate(): void {
    // TODO : recup toutes les listes de plats à créer et envoie la requete
    console.log(this.nbForms.length);
  }
}
