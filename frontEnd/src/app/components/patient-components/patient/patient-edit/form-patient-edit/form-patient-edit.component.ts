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
import { Patient } from "src/app/models/patient/patient";
import { Room } from "src/app/models/clinic/room";
import { PatientDTO } from "src/app/models/dto/patient/patientDTO";
import { AddressDTO } from "src/app/models/dto/patient/addressDTO";
import { TextureDTO } from "src/app/models/dto/food/textureDTO";
import { DietDTO } from "src/app/models/dto/patient/dietDTO";
import { AllergyDTO } from "src/app/models/dto/patient/allergyDTO";
import { CommentDTO } from "src/app/models/dto/patient/commentDTO";
import { LoginService } from "src/app/services/login/login.service";
import { PatientService } from "src/app/services/patient/patient.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";

@Component({
  selector: "app-form-patient-edit",
  templateUrl: "./form-patient-edit.component.html",
  styleUrls: ["./form-patient-edit.component.scss"],
})
export class FormPatientEditComponent implements OnInit {
  addLogo = faPlus;
  removeLogo = faMinus;

  @Input() patient: Patient;

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

  updating: boolean = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private patientService: PatientService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.patient && this.dietsAvailable && this.texturesAvailable) {
      this.createForm();
      this.getAllergiesOfPatient();
    }
  }

  createForm(): void {
    const target = {
      firstName: [
        this.patient.firstName,
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      lastName: [
        this.patient.lastName,
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      sex: [{ value: this.patient.sex, disabled: true }, Validators.required],
      dateOfBirth: [this.patient.dateOfBirth],
      situation: [this.patient.situation],
      job: [this.patient.job],
      email: [this.patient.email, Validators.email],
      phoneNumber: [
        this.patient.phoneNumber ? this.patient.phoneNumber.substr(1) : null,
        Validators.pattern("[0-9]{9}"),
      ],
      mobilePhone: [
        this.patient.mobilePhone ? this.patient.mobilePhone.substr(1) : null,
        Validators.pattern("[0-9]{9}"),
      ],
      streetName: [this.patient.address?.streetName],
      postalCode: [
        this.patient.address?.postalCode,
        Validators.pattern("[0-9]{5}"),
      ],
      city: [this.patient.address?.city],
      height: [this.patient.height, [Validators.min(0), Validators.max(251)]],
      weight: [this.patient.weight, [Validators.min(0), Validators.max(597)]],
      bloodGroup: [this.patient.bloodGroup],
      diets: this.buildCheckboxes(),
      texture: [this.patient.texture?.name, Validators.required],
      allergy: [null],
      comment: [this.patient.comment?.content],
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
      return new FormControl(this.isChecked(c));
    });
    return this.formBuilder.array(list, this.atLeastOneValidators());
  }

  isChecked(checkbox: FormCheckbox): boolean {
    return this.patient.diets.find((d) => d.name === checkbox.name)
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

  getAllergiesOfPatient(): void {
    if (this.patient.allergies) {
      this.patient.allergies.forEach((a) => this.allergies.push(a.name));
    }
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
      this.patient.id,
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
            new DietDTO(this.dietsAvailable[i].id, this.dietsAvailable[i].name)
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
    this.updating = true;
    const dto = this.getPatientDTO();
    this.patientService.updatePatient(dto).subscribe(
      (data) => {
        const id = data.id;
        this.updating = false;
        this.toastrService.success(
          "Les informations du patient ont bien été mises à jour",
          "Mise à jour terminée !"
        );
        this.router.navigate(["/patient/details"], { queryParams: { id: id } });
      },
      (error) => {
        this.updating = false;
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
