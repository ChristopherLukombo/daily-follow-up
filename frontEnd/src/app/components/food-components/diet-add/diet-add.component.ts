import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormArray,
  FormControl,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { DietDTO } from "src/app/models/dto/patient/dietDTO";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-diet-add",
  templateUrl: "./diet-add.component.html",
  styleUrls: ["./diet-add.component.scss"],
})
export class DietAddComponent implements OnInit {
  caracteristics: string[] = [
    "Calories",
    "Proteines",
    "Glucides",
    "Lipides",
    "Sucres",
    "Fibres",
    "A.G saturés",
    "Sel",
  ];
  propertyNamesOfContent: Map<string, string> = new Map([
    [this.caracteristics[0], "calories"],
    [this.caracteristics[1], "protein"],
    [this.caracteristics[2], "carbohydrate"],
    [this.caracteristics[3], "lipids"],
    [this.caracteristics[4], "sugars"],
    [this.caracteristics[5], "foodFibres"],
    [this.caracteristics[6], "agSaturates"],
    [this.caracteristics[7], "salt"],
  ]);

  HIGH_QUANTITY: number = 1;
  LOW_QUANTITY: number = 0;

  form: FormGroup;
  submitted: boolean = false;
  creating: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private alimentationService: AlimentationService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    const target = {
      name: [null, Validators.required],
      highElements: this.buildCheckboxes(),
      lowElements: this.buildCheckboxes(),
    };
    this.form = this.formBuilder.group(target);
  }

  buildCheckboxes(): FormArray {
    let checkboxes: FormCheckbox[] = [];
    this.caracteristics.forEach((c) => {
      checkboxes.push(new FormCheckbox(c));
    });
    const list = checkboxes.map((c) => {
      return new FormControl(c.selected);
    });
    return this.formBuilder.array(list);
  }

  get f() {
    return this.form.controls;
  }

  get highElements() {
    return this.form.controls.highElements["controls"];
  }

  get lowElements() {
    return this.form.controls.lowElements["controls"];
  }

  getContentPropertiesName(): Map<string, number> {
    let elementsToCheck: Map<string, number> = new Map();
    this.form.controls.highElements["controls"].forEach(
      (element: FormControl, i: number) => {
        if (element.value && this.caracteristics[i]) {
          elementsToCheck.set(
            this.propertyNamesOfContent.get(this.caracteristics[i]),
            this.HIGH_QUANTITY
          );
        }
      }
    );
    this.form.controls.lowElements["controls"].forEach(
      (element: FormControl, i: number) => {
        if (element.value && this.caracteristics[i]) {
          elementsToCheck.set(
            this.propertyNamesOfContent.get(this.caracteristics[i]),
            this.LOW_QUANTITY
          );
        }
      }
    );
    return elementsToCheck;
  }

  getDietDTO(): DietDTO {
    return new DietDTO(
      null,
      this.f.name.value,
      this.getContentPropertiesName()
    );
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.creating = true;
    console.log("submit");
    console.log(this.getContentPropertiesName());
    let dto: DietDTO = this.getDietDTO();
    this.alimentationService.createDiet(dto).subscribe(
      (data) => {
        console.log(data);
        this.toastrService.success(
          "Le régime a bien été créé",
          "Création terminée !"
        );
        this.creating = false;
        this.router.navigate(["/food/menu/currents"]);
      },
      (error) => {
        this.toastrService.error(this.getCustomError(error), "Oops !");
        this.creating = false;
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else if (error && error.status === 409) {
      return error.error.message;
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
