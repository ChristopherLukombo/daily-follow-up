import { Component, OnInit } from "@angular/core";
import { faPlus, faRedo } from "@fortawesome/free-solid-svg-icons";
import { ContentDTO } from "src/app/models/dto/food/contentDTO";
import { FormGroup, FormControl } from "@angular/forms";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";

@Component({
  selector: "app-meals-add",
  templateUrl: "./meals-add.component.html",
  styleUrls: ["./meals-add.component.scss"],
})
export class MealsAddComponent implements OnInit {
  moreLogo = faPlus;
  revertLogo = faRedo;
  typeMeals: string[] = ["Entrée", "Plat", "Garniture", "P.L", "Dessert"];

  idsForms: number[] = [1];
  mealsToCreate: ContentDTO[] = [];

  forms: FormGroup[] = [];
  creating: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  createForm(id: number): void {
    this.idsForms.push(id + 1);
  }

  deleteForm(id: number): void {
    const index = this.idsForms.indexOf(id);
    this.idsForms.splice(index, 1);
    this.forms.splice(index, 1);
  }

  submitForm(form: FormGroup): void {
    let actual: FormGroup = this.forms.find(
      (f) => f.controls.idForm.value === form.controls.idForm.value
    );
    if (!actual) {
      this.forms.push(form);
    } else {
      const index = this.idsForms.indexOf(form.controls.idForm.value);
      if (index > -1) {
        this.forms[index] = form;
      }
    }
  }

  onCreate(): void {
    this.creating = true;
    let list: ContentDTO[] = [];
    this.forms.forEach((f) => {
      list.push(this.getContentDTO(f));
    });
    this.alimentationService.createAllContent(list).subscribe(
      (data) => {
        const successfulMsg =
          data.length === 1
            ? `Le plat a bien été crée`
            : `Les ${data.length} plats ont bien été crées`;
        this.toastrService.success(successfulMsg, "Création terminée !");
        this.creating = false;
        this.router.navigate(["/food/menu/add"]);
      },
      (error) => {
        this.creating = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  getContentDTO(form: FormGroup): ContentDTO {
    return new ContentDTO(
      null,
      form.controls.name.value,
      null,
      null,
      null,
      form.controls.calories.value,
      form.controls.protein.value,
      form.controls.carbohydrate.value,
      form.controls.lipids.value,
      form.controls.sugars.value,
      null,
      form.controls.agSaturates.value,
      form.controls.salt.value,
      this.getTypeMeals(form),
      null,
      form.controls.mixed.value
    );
  }

  getTypeMeals(form: FormGroup): string[] {
    let types: string[] = [];
    form.controls.types["controls"].forEach((type: FormControl, i: number) => {
      if (type.value === true && this.typeMeals[i]) {
        types.push(this.typeMeals[i]);
      }
    });
    return types;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
