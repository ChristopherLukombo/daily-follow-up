import { Component, OnInit, Output, EventEmitter, Input } from "@angular/core";
import {
  Validators,
  FormGroup,
  FormBuilder,
  FormArray,
  FormControl,
  ValidatorFn,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { faArrowRight, faCheckCircle } from "@fortawesome/free-solid-svg-icons";
import { ExternalApiService } from "src/app/services/external-api/external-api.service";
import { debounceTime, distinctUntilChanged, switchMap } from "rxjs/operators";
import { Dish } from "src/app/models/external-api/dish";
import { Content } from "src/app/models/food/content";

@Component({
  selector: "app-form-meal-add",
  templateUrl: "./form-meal-add.component.html",
  styleUrls: ["./form-meal-add.component.scss"],
})
export class FormMealAddComponent implements OnInit {
  infosLogo = faArrowRight;
  successLogo = faCheckCircle;

  @Input() idForm = 0;
  form: FormGroup;
  typeMeals: string[] = ["Entrée", "Plat", "Garniture", "P.L", "Dessert"];
  searchList: Dish[] = [];
  selectedContent: Content;
  submittedSearch: boolean = false;
  submitted: boolean = false;
  @Output() formToSubmit = new EventEmitter<FormGroup>();

  loading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private externalApiService: ExternalApiService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.spySearchInput();
  }

  ngOnChanges(): void {
    if (this.form) {
      this.f.idForm.setValue(this.idForm);
    }
  }

  createForm() {
    const target = {
      idForm: [this.idForm, Validators.required],
      types: this.buildCheckboxes(),
      name: [null, Validators.required],
      mixed: [false],
      calories: [
        null,
        [Validators.required, Validators.min(0), Validators.max(900)],
      ],
      protein: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      carbohydrate: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      lipids: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      sugars: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      agSaturates: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      salt: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
    };
    this.form = this.formBuilder.group(target);
  }

  spySearchInput(): void {
    this.f.name.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        switchMap((search) => {
          return this.externalApiService.searchMeals(search);
        })
      )
      .subscribe(
        (data) => {
          this.searchList = data ? data : [];
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

  selectMeal(dish: Dish) {
    this.searchList = [];
    this.f.name.setValue(dish.name, { emitEvent: false });
  }

  onGetDetails(): void {
    this.submittedSearch = true;
    this.loading = true;
    this.externalApiService.matchMeal(this.f.name.value).subscribe(
      (data) => {
        this.selectedContent = this.externalApiService.toContent(data);
        this.loading = false;
      },
      (error) => {
        console.log(error);
        this.loading = false;
      }
    );
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.form.disable({ emitEvent: false });
    this.formToSubmit.emit(this.form);
  }

  onEdit(): void {
    this.form.enable({ emitEvent: false });
  }
}
