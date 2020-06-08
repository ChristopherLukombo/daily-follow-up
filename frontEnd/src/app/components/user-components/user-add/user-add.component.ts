import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ClinicService } from "src/app/services/clinic/clinic.service";
import { Floor } from "src/app/models/clinic/floor";
import { UserDTO } from "src/app/models/dto/user/userDTO";
import { Role } from "src/app/models/user/role-enum";
import { CaregiverDTO } from "src/app/models/dto/user/caregiverDTO";
import { UserService } from "src/app/services/user/user.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-user-add",
  templateUrl: "./user-add.component.html",
  styleUrls: ["./user-add.component.scss"],
})
export class UserAddComponent implements OnInit {
  illegalsChar: string = "&'\"(`_)+$^*!:;,<\\>.-/=}]°@|[{#~¨$£%?§¤";

  roles: Map<string, string> = new Map([
    ["Aide-soignant", Role.ROLE_CAREGIVER],
    ["Diététicien", Role.ROLE_NUTRITIONIST],
  ]);
  selectedRole: string;

  floors: Floor[] = [];

  form: FormGroup;
  submitted: boolean = false;

  error: string;
  loading: boolean = false;
  creating: boolean = false;
  createdMessage: string;

  constructor(
    private formBuilder: FormBuilder,
    private clinicService: ClinicService,
    private userService: UserService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.createdMessage = this.getCreatedMsg("dede", "dede");
    this.loading = true;
    this.clinicService.getAllFloors().subscribe(
      (data) => {
        this.floors = data;
        this.createForm();
        this.selectRole(this.roles.keys().next().value);
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
        [Validators.required, Validators.pattern("[A-Za-z]{2,20}")], // TODO : gérer les accents
      ],
      lastName: [
        "",
        [Validators.required, Validators.pattern("[A-Za-z]{2,20}")],
      ],
      floor: [null],
    };
    this.form = this.formBuilder.group(target);
  }

  get f() {
    return this.form.controls;
  }

  selectRole(key: string): void {
    this.createdMessage = null;
    this.selectedRole = this.roles.get(key);
    if (this.selectedRole === Role.ROLE_CAREGIVER) {
      this.f.floor.setValidators([Validators.required]);
    } else {
      this.f.floor.clearValidators();
    }
    this.f.floor.setValue(null);
  }

  selectFloor(floor: Floor): void {
    this.f.floor.setValue(floor);
  }

  validName(): void {
    this.illegalsChar.split("").forEach((i) => {
      if (this.f.firstName.value.includes(i)) {
        this.f.firstName.invalid;
        return;
      }
      if (this.f.lastName.value.includes(i)) {
        this.f.lastName.invalid;
        return;
      }
    });
  }

  generateLogin(firstName: string, lastName: string): string {
    return lastName.toLowerCase() + "_" + firstName.charAt(0).toLowerCase();
  }

  createCaregiver(user: UserDTO, floorId: number): void {
    let dto = new CaregiverDTO(null, floorId, user);
    this.userService.createCaregiver(dto).subscribe(
      (data) => {
        this.creating = false;
        this.toastrService.success(
          "L'utilisateur " + data.user.pseudo + " a bien été crée",
          "Création terminée !"
        );
        this.createdMessage = this.getCreatedMsg(
          dto.user.pseudo,
          dto.user.password
        );
      },
      (error) => {
        this.creating = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  createNutritionist(user: UserDTO): void {
    this.userService.createUser(user).subscribe(
      (data) => {
        this.creating = false;
        this.toastrService.success(
          "L'utilisateur " + data.pseudo + " a bien été crée",
          "Création terminée !"
        );
        this.createdMessage = this.getCreatedMsg(user.pseudo, user.password);
      },
      (error) => {
        this.creating = false;
        this.toastrService.error(this.getError(error), "Oops !");
      }
    );
  }

  onSubmit(): void {
    this.createdMessage = null;
    this.validName();
    this.submitted = true;
    if (this.form.invalid) return;
    this.creating = true;
    let password: string = "Motdepasse!" + Math.floor(Math.random() * 10) + 10;
    let login: string = this.generateLogin(
      this.f.firstName.value,
      this.f.lastName.value
    );
    let user: UserDTO = new UserDTO(
      null,
      login,
      password,
      this.f.firstName.value,
      this.f.lastName.value,
      null,
      true,
      null,
      this.selectedRole,
      false
    );
    if (this.selectedRole === Role.ROLE_CAREGIVER) {
      this.createCaregiver(user, this.f.floor.value.id);
    } else if (this.selectedRole === Role.ROLE_NUTRITIONIST) {
      this.createNutritionist(user);
    }
  }

  getCreatedMsg(pseudo: string, password: string): string {
    return (
      "L'utilisateur <strong><code>" +
      pseudo +
      "</code></strong> a bien été crée. Son mot de passe temporaire est : <strong><code>" +
      password +
      "</code></strong><br/>Le mot de passe sera modifié lors de la première connexion à l'application."
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else if (error && error === 403) {
      return "Vous n'êtes plus habilité à cette requête.";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
