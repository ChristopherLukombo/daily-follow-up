import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Diet } from "src/app/models/patient/diet";
import { TypeMessage } from "src/app/models/utils/message-enum";
import {
  FormGroup,
  Validators,
  FormBuilder,
  FormArray,
  FormControl,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { HttpErrorResponse } from "@angular/common/http";
import { DietDTO } from "src/app/models/dto/patient/dietDTO";
import { ToastrService } from "ngx-toastr";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-diet-edit",
  templateUrl: "./diet-edit.component.html",
  styleUrls: ["./diet-edit.component.scss"],
})
export class DietEditComponent implements OnInit {
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

  diet: Diet;

  loading: boolean = false;
  warning: string;
  error: string;

  form: FormGroup;
  submitted: boolean = false;
  updating: boolean = false;

  btnDelete: string = "Supprimer le régime";
  confirmDelete: string =
    "Le régime alimentaire sera supprimé de la clinique. \
    Il ne sera donc plus disponible ni lors des futurs déclinaisons de menu, \
    ni lors des enregistrements des régimes de patients. Veuillez confirmer pour continuer.";
  deleting: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private alimentationService: AlimentationService,
    private formBuilder: FormBuilder,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.route.queryParams.subscribe((params) => {
      this.alimentationService.getDiet(parseInt(params["id"])).subscribe(
        (data) => {
          if (data) {
            this.diet = data;
            this.createForm();
          } else {
            this.dietDoesNotExist();
          }
          this.loading = false;
        },
        (error) => {
          this.error = this.getError(error);
          this.loading = false;
        }
      );
    });
  }

  dietDoesNotExist(): void {
    this.warning = TypeMessage.DIET_DOES_NOT_EXIST;
  }

  createForm(): void {
    const target = {
      name: [this.diet.name, Validators.required],
      highElements: this.buildCheckboxes(this.HIGH_QUANTITY),
      lowElements: this.buildCheckboxes(this.LOW_QUANTITY),
    };
    this.form = this.formBuilder.group(target);
  }

  buildCheckboxes(quantity: number): FormArray {
    let checkboxes: FormCheckbox[] = [];
    this.caracteristics.forEach((c) => {
      checkboxes.push(new FormCheckbox(c));
    });
    const list = checkboxes.map((c) => {
      return new FormControl(this.isChecked(c, quantity));
    });
    return this.formBuilder.array(list);
  }

  isChecked(checkbox: FormCheckbox, quantity: number): boolean {
    let element: string = this.propertyNamesOfContent.get(checkbox.name);
    return this.diet.elementsToCheck.has(element) &&
      this.diet.elementsToCheck.get(element) === quantity
      ? true
      : false;
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
      this.diet.id,
      this.f.name.value.charAt(0).toUpperCase() + this.f.name.value.slice(1),
      this.getContentPropertiesName()
    );
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.updating = true;
    let dto: DietDTO = this.getDietDTO();
    this.alimentationService.updateDiet(dto).subscribe(
      (data) => {
        this.toastrService.success(
          "Le régime a bien été mis à jour",
          "Mise à jour terminée !"
        );
        this.updating = false;
        this.router.navigate(["/food/diet/add"]);
      },
      (error) => {
        this.toastrService.error(this.getCustomError(error), "Oops !");
        this.updating = false;
      }
    );
  }

  onDelete(): void {
    this.deleting = true;
    this.alimentationService.deleteDiet(this.diet.id).subscribe(
      (data) => {
        this.deleting = false;
        this.toastrService.success(
          "Le régime a bien été supprimé",
          "Suppression réussie !"
        );
        this.router.navigate(["/food/diet/add"]);
      },
      (error) => {
        this.deleting = false;
        this.toastrService.error(this.getCustomError(error), "Oops !");
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

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 409) {
      return error.error.message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
