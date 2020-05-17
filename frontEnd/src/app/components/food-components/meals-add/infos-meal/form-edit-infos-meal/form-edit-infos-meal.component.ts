import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-form-edit-infos-meal",
  templateUrl: "./form-edit-infos-meal.component.html",
  styleUrls: ["./form-edit-infos-meal.component.scss"],
})
export class FormEditInfosMealComponent implements OnInit {
  illegalInputInt: string[] = [".", "e", ",", "-", "+", "*"];
  illegalInputFloat: string[] = ["e", "-", "+", "*"];

  form: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    const target = {
      calories: [null, Validators.required],
      protein: [null, Validators.required],
      carbohydrate: [null, Validators.required], // TODO : à retirer
      lipids: [null, Validators.required],
      sugars: [null, Validators.required],
      foodFibres: [null, Validators.required],
      agSaturates: [null, Validators.required],
      salt: [null, Validators.required],
    };
    this.form = this.formBuilder.group(target);
  }

  get f() {
    return this.form.controls;
  }

  validateInt(event: KeyboardEvent): void {
    if (this.illegalInputInt.indexOf(event.key) !== -1) {
      event.preventDefault();
    }
  }

  validateFloat(event: KeyboardEvent): void {
    if (this.illegalInputFloat.indexOf(event.key) !== -1) {
      event.preventDefault();
    }
  }

  onSubmit(): void {}
}
