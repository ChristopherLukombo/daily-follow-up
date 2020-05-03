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
  ValidatorFn,
} from "@angular/forms";
import { FormCheckbox } from "src/app/models/utils/form-checkbox";
import { forkJoin } from "rxjs";
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

  creating: boolean = false;

  loading: boolean = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private alimentationService: AlimentationService,
    private loginService: LoginService,
    private patientService: PatientService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loading = true;
    let allDiets = this.alimentationService.getAllDiets();
    let allTextures = this.alimentationService.getAllTextures();
    forkJoin([allDiets, allTextures]).subscribe(
      (datas) => {
        this.dietsAvailable = datas[0];
        this.texturesAvailable = datas[1];
        this.createForm();
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
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
      height: [null, [Validators.min(0), Validators.max(251)]],
      weight: [null, [Validators.min(0), Validators.max(597)]],
      bloodGroup: [this.bloodGroups[0]],
      diets: this.buildCheckboxes(),
      texture: ["", Validators.required],
      allergy: [""],
      comment: [""],
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
    if (allergy && allergy !== "") {
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

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.creating = true;
    const dto = this.getPatientDTO();
    console.log(dto);
    this.patientService.createPatient(dto).subscribe(
      (data) => {
        const id = data.id;
        this.creating = false;
        this.toastrService.success(
          "Le patient a bien été crée",
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
      "01".concat(this.f.phoneNumber.value),
      "06".concat(this.f.mobilePhone.value),
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
      this.f.streetName.value === "" ||
      this.f.city.value === "" ||
      this.f.postalCode.value === ""
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
    if (this.f.comment.value === "") return null;
    return new CommentDTO(
      null,
      this.f.comment.value,
      this.loginService.getTokenPseudo(),
      new Date()
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
