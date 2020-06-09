import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormArray,
  FormControl,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";

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

  form: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    const target = {
      name: [null, Validators.required],
      elementsToCheck: this.buildCheckboxes(),
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

  get elementsToCheck() {
    return this.form.controls.elementsToCheck["controls"];
  }

  getContentPropertiesName(): string[] {
    let properties: Map<string, string> = new Map([
      [this.caracteristics[0], "calories"],
      [this.caracteristics[1], "protein"],
      [this.caracteristics[2], "carbohydrate"],
      [this.caracteristics[3], "lipids"],
      [this.caracteristics[4], "sugars"],
      [this.caracteristics[5], "foodFibres"],
      [this.caracteristics[6], "agSaturates"],
      [this.caracteristics[7], "salt"],
    ]);
    let elementsToCheck: string[] = [];
    this.form.controls.elementsToCheck["controls"].forEach(
      (element: FormControl, i: number) => {
        if (element.value === true && this.caracteristics[i]) {
          elementsToCheck.push(properties.get(this.caracteristics[i]));
        }
      }
    );
    return elementsToCheck;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    console.log("submit");
    console.log(this.getContentPropertiesName());
  }
}
