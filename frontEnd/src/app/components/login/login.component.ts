import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { LoginDTO } from "src/app/models/dto/loginDTO";
import { LoginService } from "src/app/services/login/login.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  loginForm: FormGroup;
  errorForm: string;
  closeErrorForm: Boolean = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    const target = {
      username: ["", [Validators.required, Validators.maxLength(50)]],
      password: ["", [Validators.required, Validators.maxLength(50)]],
    };
    this.loginForm = this.formBuilder.group(target);
  }

  get f() {
    return this.loginForm.controls;
  }

  onLogin(): void {
    this.cleanErrorMessages();
    if (this.loginForm.invalid) {
      console.log("ok invalid");
      this.errorForm = "Veuillez remplir tout les champs.";
      console.log(this.errorForm);
      return;
    }
    let loginDTO: LoginDTO = new LoginDTO(
      this.loginForm.controls.username.value,
      this.loginForm.controls.password.value
    );
    this.loginService.login(loginDTO).subscribe(
      (data) => {
        localStorage.setItem("token", data.id_token);
        this.router.navigate(["/patient"]);
      },
      (error) => {
        this.catchError(error);
      }
    );
  }

  /**
   * récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error != undefined && error == 403) {
      this.error =
        "Le nom d'utilisateur et le mot de passe ne correspondent pas.";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard";
    }
  }

  cleanErrorMessages(): void {
    this.errorForm = undefined;
    this.error = undefined;
  }
}
