import { Component, OnInit } from "@angular/core";
import { faPlus, faRedo } from "@fortawesome/free-solid-svg-icons";
import { ContentDTO } from "src/app/models/dto/food/contentDTO";
import { FormGroup } from "@angular/forms";

@Component({
  selector: "app-meals-add",
  templateUrl: "./meals-add.component.html",
  styleUrls: ["./meals-add.component.scss"],
})
export class MealsAddComponent implements OnInit {
  moreLogo = faPlus;
  revertLogo = faRedo;

  idsForms: number[] = [1];
  mealsToCreate: ContentDTO[] = [];

  forms: FormGroup[] = [];

  constructor() {}

  ngOnInit(): void {}

  createForm(id: number): void {
    this.idsForms.push(id + 1);
  }

  deleteForm(id: number): void {
    const index = this.idsForms.indexOf(id);
    this.idsForms.splice(index, 1);
    this.forms.splice(index, 1);
    console.log(this.idsForms.length);
  }

  // TODO : 3) css sur le nb de plat créer lorsque submit form
  submitForm(form: FormGroup): void {
    let actual: FormGroup = this.forms.find(
      (f) => f.controls.idForm.value === form.controls.idForm.value
    );
    if (!actual) {
      this.forms.push(form);
      console.log("on push");
    } else {
      const index = this.idsForms.indexOf(form.controls.idForm.value);
      if (index > -1) {
        console.log("on update");
        this.forms[index] = form;
      }
    }
  }

  onCreate(): void {
    // TODO : recup toutes les listes de plats à créer et envoie la requete
    console.log(this.idsForms);
    console.log(this.forms);
  }
}
