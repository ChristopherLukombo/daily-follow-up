import { Component, OnInit } from "@angular/core";
import {
  Validators,
  FormGroup,
  FormBuilder,
  FormArray,
  FormControl,
  ValidatorFn,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { ExternalApiService } from "src/app/services/external-api/external-api.service";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs/operators";

@Component({
  selector: "app-form-meal-add",
  templateUrl: "./form-meal-add.component.html",
  styleUrls: ["./form-meal-add.component.scss"],
})
export class FormMealAddComponent implements OnInit {
  infosLogo = faSearch;

  form: FormGroup;
  typeMeals: string[] = [
    "Entrée",
    "Plat",
    "Garniture",
    "Produit laitier",
    "Dessert",
  ];
  submitted: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private externalApiService: ExternalApiService
  ) {}
  // <input(keyup)="search$.next($event.target.value)">

  ngOnInit(): void {
    this.createForm();
    this.spySearchInput();
  }

  createForm() {
    const target = {
      types: this.buildCheckboxes(),
      name: [null, Validators.required],
    };
    this.form = this.formBuilder.group(target);
  }

  spySearchInput(): void {
    this.f.name.valueChanges
      .pipe(
        debounceTime(2000),
        distinctUntilChanged(),
        switchMap((search) => {
          console.log("switch" + search);
          return this.externalApiService.searchMeals(search);
        }) //, catchError(err => of(null))
      )
      .subscribe(
        (data) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  get f() {
    return this.form.controls;
  }

  get types() {
    return this.form.controls.types["controls"];
  }

  buildCheckboxes(): FormArray {
    let checkboxes: FormCheckbox[] = [];
    this.typeMeals.forEach((d) => {
      checkboxes.push(new FormCheckbox(d));
    });
    const list = checkboxes.map((c) => {
      return new FormControl(c.selected);
    });
    return this.formBuilder.array(list, this.atLeastOneValidators());
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
    if (this.form.invalid) {
      return;
    }
    console.log("ok");
    this.externalApiService.searchMeals(this.f.name.value).subscribe(
      (data) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
    // chaque plat à tous les texture possible
    //console.log(this.getTypeMeals());
    //console.log(this.f.name.value);
  }
}
