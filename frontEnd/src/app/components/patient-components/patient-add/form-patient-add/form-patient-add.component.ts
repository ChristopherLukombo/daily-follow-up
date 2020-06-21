import { Component, OnInit, Input } from "@angular/core";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import {
  FormGroup,
  Validators,
  FormBuilder,
  FormControl,
  FormArray,
  ValidatorFn,
} from "@angular/forms";
import { Diet } from "src/app/models/patient/diet";
import { Texture } from "src/app/models/food/texture";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { Room } from "src/app/models/clinic/room";
import { PatientDTO } from "src/app/models/dto/patient/patientDTO";
import { TextureDTO } from "src/app/models/dto/food/textureDTO";
import { AddressDTO } from "src/app/models/dto/patient/addressDTO";
import { DietDTO } from "src/app/models/dto/patient/dietDTO";
import { AllergyDTO } from "src/app/models/dto/patient/allergyDTO";
import { CommentDTO } from "src/app/models/dto/patient/commentDTO";
import { LoginService } from "src/app/services/login/login.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";

@Component({
  selector: "app-form-patient-add",
  templateUrl: "./form-patient-add.component.html",
  styleUrls: ["./form-patient-add.component.scss"],
})
export class FormPatientAddComponent implements OnInit {
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
  @Input() dietsAvailable: Diet[] = [];
  @Input() texturesAvailable: Texture[] = [];
  allergies: string[] = [];

  creating: boolean = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private patientService: PatientService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.dietsAvailable && this.texturesAvailable) this.createForm();
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
      dateOfBirth: [null],
      situation: [this.situations[0]],
      job: [this.jobs[0]],
      email: [null, Validators.email],
      phoneNumber: [null, Validators.pattern("[0-9]{9}")],
      mobilePhone: [null, Validators.pattern("[0-9]{9}")],
      streetName: [null],
      postalCode: [null, Validators.pattern("[0-9]{5}")],
      city: [null],
      height: [null, [Validators.min(0), Validators.max(251)]],
      weight: [null, [Validators.min(0), Validators.max(597)]],
      bloodGroup: [this.bloodGroups[0]],
      diets: this.buildCheckboxes(),
      texture: ["", Validators.required],
      allergy: [""],
      comment: [null],
      room: [null, Validators.required],
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

  get f() {
    return this.form.controls;
  }

  get diets(): FormArray {
    return this.form.controls.diets["controls"];
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
    if (allergy && allergy !== "" && this.allergies.length < 5) {
      this.allergies.push(allergy);
    }
  }

  deleteAllergy(allergy: string): void {
    let i = this.allergies.indexOf(allergy);
    this.allergies.splice(i, 1);
  }

  setRoom(room: Room): void {
    this.form.controls.room.setValue(room);
  }

  /**
   * Géneration du DTO
   * @return le DTO du patient
   */
  getPatientDTO(): PatientDTO {
    const dto = new PatientDTO(
      null,
      this.f.firstName.value,
      this.f.lastName.value,
      this.f.email.value,
      this.f.situation.value,
      this.f.dateOfBirth.value,
      this.getAdress(),
      this.f.phoneNumber.value ? "0".concat(this.f.phoneNumber.value) : null,
      this.f.mobilePhone.value ? "0".concat(this.f.mobilePhone.value) : null,
      this.f.job.value,
      this.f.bloodGroup.value,
      parseInt(this.f.height.value),
      parseFloat(this.f.weight.value),
      this.f.sex.value,
      true,
      this.getTexture(),
      this.getDiets(),
      this.getAllergies(),
      null,
      this.getComment(),
      this.f.room.value.id
    );
    return dto;
  }

  getAdress(): AddressDTO {
    if (
      !this.f.streetName.value ||
      !this.f.city.value ||
      !this.f.postalCode.value
    )
      return null;
    return new AddressDTO(
      null,
      this.f.streetName.value,
      this.f.city.value,
      this.f.postalCode.value
    );
  }

  getTexture(): TextureDTO {
    let id: number = this.texturesAvailable.find(
      (t) => t.name === this.f.texture.value
    ).id;
    if (!id) return null;
    return new TextureDTO(id, this.f.texture.value);
  }

  getDiets(): Array<DietDTO> {
    let diets: Array<DietDTO> = new Array<DietDTO>();
    this.form.controls.diets["controls"].forEach(
      (diet: FormControl, i: number) => {
        if (diet.value === true && this.dietsAvailable[i]) {
          diets.push(
            new DietDTO(
              this.dietsAvailable[i].id,
              this.dietsAvailable[i].name,
              this.dietsAvailable[i].elementsToCheck
            )
          );
        }
      }
    );
    return diets;
  }

  getAllergies(): Array<AllergyDTO> {
    let allergies: Array<AllergyDTO> = new Array<AllergyDTO>();
    this.allergies.forEach((name) => {
      allergies.push(new AllergyDTO(null, name));
    });
    return allergies;
  }

  getComment(): CommentDTO {
    if (!this.f.comment.value) return null;
    return new CommentDTO(
      null,
      this.f.comment.value,
      this.loginService.getTokenPseudo(),
      new Date()
    );
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.creating = true;
    const dto = this.getPatientDTO();
    this.patientService.createPatient(dto).subscribe(
      (data) => {
        const id = data.id;
        this.creating = false;
        this.toastrService.success(
          "Le patient a bien été créé",
          "Création terminée !"
        );
        this.router.navigate(["/patient/details"], { queryParams: { id: id } });
      },
      (error) => {
        this.creating = false;
        this.toastrService.error(this.getError(error), "Oops !");
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
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
