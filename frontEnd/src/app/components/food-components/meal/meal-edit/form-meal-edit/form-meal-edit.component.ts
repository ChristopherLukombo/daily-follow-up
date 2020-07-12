import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import {
  FormBuilder,
  Validators,
  FormArray,
  ValidatorFn,
  FormControl,
  FormGroup,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { Label, SingleDataSet } from "ng2-charts";
import { ChartType } from "chart.js";
import { ContentDTO } from "src/app/models/dto/food/contentDTO";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { HttpErrorResponse } from "@angular/common/http";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-form-meal-edit",
  templateUrl: "./form-meal-edit.component.html",
  styleUrls: ["./form-meal-edit.component.scss"],
})
export class FormMealEditComponent implements OnInit {
  illegalInputFloat: string[] = ["e", "-", "+", "*"];
  @Input() content: Content;
  typeMeals: string[] = ["Entrée", "Plat", "Garniture", "P.L", "Dessert"];

  doughnutChartLabels: Label[] = [
    "Protéines",
    "Glucides",
    "Lipides",
    "Sucres",
    "Fibres",
    "AG saturés",
    "Sel chlorire de sodium",
  ];
  doughnutChartoptions = {
    title: {
      display: true,
      fontSize: 13,
    },
    legend: { position: "right" },
  };
  doughnutChartData: SingleDataSet = new Array<number>(7);
  doughnutChartType: ChartType = "doughnut";

  form: FormGroup;
  submitted: boolean = false;
  updating: boolean = false;

  btnDelete: string = "Supprimer le plat";
  confirmDelete: string =
    "Le plat sera supprimé de la clinique. Veuillez confirmer pour continuer.";
  deleting: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private alimentationService: AlimentationService,
    private router: Router,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    if (this.content) {
      this.createForm();
    }
  }

  ngOnChanges(): void {
    this.doughnutChartData = this.content
      ? this.generateChartData(this.content)
      : [0, 0, 0, 0, 0, 0, 0];
  }

  createForm() {
    const target = {
      name: [this.content.name, Validators.required],
      mixed: [this.content.mixed, Validators.required],
      types: this.buildCheckboxes(),
      calories: [
        this.content.calories,
        [Validators.required, Validators.min(0), Validators.max(900)],
      ],
      protein: [
        this.content.protein,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      carbohydrate: [
        this.content.carbohydrate,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      lipids: [
        this.content.lipids,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      sugars: [
        this.content.sugars,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      foodFibres: [
        this.content.foodFibres,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      agSaturates: [
        this.content.agSaturates,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      salt: [
        this.content.salt,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
    };
    this.form = this.formBuilder.group(target);
  }

  get f() {
    return this.form.controls;
  }

  get types() {
    return this.form.controls.types["controls"];
  }

  buildCheckboxes(): FormArray {
    let checkboxes: FormCheckbox[] = [];
    this.typeMeals.forEach((t) => {
      checkboxes.push(new FormCheckbox(t));
    });
    const list = checkboxes.map((c) => {
      return new FormControl(this.isChecked(c));
    });
    return this.formBuilder.array(list, this.atLeastOneValidators());
  }

  isChecked(checkbox: FormCheckbox): boolean {
    return this.content.typeMeals.find((t) => t === checkbox.name)
      ? true
      : false;
  }

  atLeastOneValidators(min: number = 1): ValidatorFn {
    const validator: ValidatorFn = (formArray: FormArray) => {
      const totalSelected = formArray.controls
        .map((control) => control.value)
        .reduce((prev, next) => (next ? prev + next : prev), 0);
      return totalSelected >= min ? null : { required: true };
    };
    return validator;
  }

  generateChartData(data: Content): number[] {
    let set: number[] = [];
    set[0] = data.protein;
    set[1] = data.carbohydrate;
    set[2] = data.lipids;
    set[3] = data.sugars;
    set[4] = data.foodFibres;
    set[5] = data.agSaturates;
    set[6] = data.salt;
    return set;
  }

  chartEmpty(): boolean {
    const set: number[] = this.doughnutChartData as number[];
    return set.filter((data: number) => !data).length == 7;
  }

  updateValues(event: KeyboardEvent, key: string, index?: number): void {
    if (this.illegalInputFloat.indexOf(event.key) !== -1) {
      event.preventDefault();
    }
    this.updateChart(key, index);
  }

  updateChart(key: string, index?: number): void {
    if (index !== undefined && key !== "calories") {
      this.doughnutChartData[index] = this.f[key].value;
      this.doughnutChartData = this.doughnutChartData.slice();
    }
  }

  getContentDTO(): ContentDTO {
    return new ContentDTO(
      this.content.id,
      this.f.name.value,
      this.content.groupName,
      this.content.subGroupName,
      this.content.subSubGroupName,
      this.f.calories.value,
      this.f.protein.value,
      this.f.carbohydrate.value,
      this.f.lipids.value,
      this.f.sugars.value,
      this.f.foodFibres.value,
      this.f.agSaturates.value,
      this.f.salt.value,
      this.getTypeMeals(),
      this.content.imageUrl,
      this.f.mixed.value
    );
  }

  getTypeMeals(): string[] {
    let types: string[] = [];
    this.types.forEach((type: FormControl, i: number) => {
      if (type.value === true && this.typeMeals[i]) {
        types.push(this.typeMeals[i]);
      }
    });
    return types;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.updating = true;
    let dto = this.getContentDTO();
    this.alimentationService.updateContent(dto).subscribe(
      (data) => {
        this.updating = false;
        this.toastrService.success(
          "Les informations du plat ont bien été mises à jour",
          "Mise à jour terminée !"
        );
        this.router.navigate(["/food/meal/all"]);
      },
      (error) => {
        this.updating = false;
        this.toastrService.error(this.getCustomError(error), "Oops !");
      }
    );
  }

  onDelete(): void {
    this.deleting = true;
    this.alimentationService.deleteContent(this.content.id).subscribe(
      (data) => {
        this.deleting = false;
        this.toastrService.success(
          "Le plat a bien été supprimé",
          "Suppression réussie !"
        );
        this.router.navigate(["/food/meal/all"]);
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
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 409) {
      return this.removeTriggerTrace(error.error.message);
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }

  /**
   * Retire les notions techniques et les messages provenant directement de la base
   * @param message
   */
  removeTriggerTrace(message: string): string {
    return message.split("Où")[0].replace("ERREUR:", "");
  }
}
