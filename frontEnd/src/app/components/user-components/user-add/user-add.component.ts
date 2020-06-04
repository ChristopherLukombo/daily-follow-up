import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-user-add",
  templateUrl: "./user-add.component.html",
  styleUrls: ["./user-add.component.scss"],
})
export class UserAddComponent implements OnInit {
  illegalsChar: string = "&'\"(`_)+$^*!:;,<\\>.-/=}]°@|[{#~¨$£%?§¤";

  roles: Map<string, string> = new Map([
    ["ROLE_CAREGIVER", "Aide-soignant"],
    ["ROLE_NUTRITIONIST", "Diététicien"],
  ]);
  role: string = this.roles.keys().next().value;
  selectedButton: number = 0;

  form: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
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
      createDate: [null, Validators.required],
    };
    this.form = this.formBuilder.group(target);
  }

  get f() {
    return this.form.controls;
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

  selectRole(role: string, index: number): void {
    this.selectedButton = index;
    this.role = role;
  }

  generateLogin(firstName: string, lastName: string): string {
    return lastName.toLowerCase() + "_" + firstName.charAt(0);
  }

  onSubmit(): void {
    this.validName();
    this.submitted = true;
    if (this.form.invalid) return;
    let password: string = "Motdepasse!" + Math.floor(Math.random() * 10) + 10;
    let login: string = this.generateLogin(
      this.f.firstName.value,
      this.f.lastName.value
    );
    console.log(this.f);
  }
}
