import { Component, OnInit } from "@angular/core";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { Diet } from "src/app/models/patient/diet";
import { Texture } from "src/app/models/food/texture";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import {
  FormGroup,
  Validators,
  FormBuilder,
  FormControl,
  FormArray,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";

@Component({
  selector: "app-patient-add",
  templateUrl: "./patient-add.component.html",
  styleUrls: ["./patient-add.component.scss"],
})
export class PatientAddComponent implements OnInit {
  addLogo = faPlus;
  removeLogo = faMinus;

  illegalInputInt: string[] = [".", "e", ",", "-", "+", "*"];
  illegalInputFloat: string[] = ["e", "-", "+", "*"];

  form: FormGroup;
  submitted: boolean = false;
  sexes: string[] = ["Homme", "Femme"];
  situations: string[] = [
    "Marié",
    "Pacsé",
    "Divorcé",
    "Separé",
    "Célibataire",
    "Veuf",
  ];
  jobs: string[] = ["En activité", "Sans activité", "Retraité"];
  bloodGroups: string[] = ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"];
  dietsAvailable: Diet[] = [];
  texturesAvailable: Texture[] = [];
  allergies: string[] = [];

  loading: boolean = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private alimentationService: AlimentationService
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllDiets().subscribe(
      (data) => {
        this.dietsAvailable = data;
        this.alimentationService.getAllTextures().subscribe(
          (data) => {
            this.texturesAvailable = data;
            this.createForm();
            this.loading = false;
          },
          (error) => {
            this.catchError(error);
            this.loading = false;
          }
        );
      },
      (error) => {
        this.catchError(error);
        this.loading = false;
      }
    );
  }

  createForm(): void {
    const target = {
      firstName: [
        "",
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      lastName: [
        "",
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      sex: [this.sexes[0], Validators.required],
      dateOfBirth: [""], // TODO : faire le check
      situation: [this.situations[0]],
      job: [this.jobs[0]],
      email: ["", Validators.email],
      phoneNumber: ["", Validators.pattern("[0-9]{9}")],
      mobilePhone: ["", Validators.pattern("[0-9]{9}")],
      streetName: [""],
      postalCode: ["", Validators.pattern("[0-9]{5}")],
      city: [""],
      height: ["", [Validators.min(0), Validators.max(251)]],
      weight: ["", [Validators.min(0), Validators.max(597)]], // TODO: faire le check sur pattern float
      bloodGroup: [this.bloodGroups[0]],
      diets: this.buildCheckboxes(),
      texture: [this.texturesAvailable[0].name],
      allergy: [""],
      comment: [""],
    };
    this.form = this.formBuilder.group(target);
  }

  buildCheckboxes(): FormArray {
    let checkboxes: FormCheckbox[] = [];
    this.dietsAvailable.forEach((d) => {
      checkboxes.push(new FormCheckbox(d.name));
    });
    const list = checkboxes.map((c) => {
      return new FormControl(c.selected);
    });
    return new FormArray(list);
  }

  get f() {
    return this.form.controls;
  }

  get diets(): FormArray {
    return this.form.controls.diets["controls"];
  }

  getSelectedDiets(): string[] {
    let selectedDiets: string[] = [];
    this.form.controls.diets["controls"].forEach(
      (diet: FormControl, i: number) => {
        if (diet.value === true && this.dietsAvailable[i]) {
          selectedDiets.push(this.dietsAvailable[i].name);
        }
      }
    );
    return selectedDiets;
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

  addAllergy(): void {
    let allergy: string = this.form.controls.allergy.value;
    if (allergy && allergy !== "") {
      this.allergies.push(allergy);
    }
  }

  deleteAllergy(allergy: string): void {
    let i = this.allergies.indexOf(allergy);
    this.allergies.splice(i, 1);
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    console.log(this.form.controls.dateOfBirth.value);
    console.log("les diets :" + this.getSelectedDiets());
    console.log(this.allergies);
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 403) {
      this.error =
        "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
