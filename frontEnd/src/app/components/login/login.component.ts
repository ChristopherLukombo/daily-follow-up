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
  submitted: Boolean = false;
  error: string;
  loading: Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  /**
   * Création du formulaire de connexion
   */
  createForm() {
    const target = {
      username: ["", Validators.required],
      password: ["", Validators.required],
    };
    this.loginForm = this.formBuilder.group(target);
  }

  get f() {
    return this.loginForm.controls;
  }

  /**
   * Connexion à l'application
   */
  onLogin(): void {
    this.submitted = true;
    this.cleanErrorMessages();
    if (this.loginForm.invalid) {
      return;
    }
    this.loading = true;
    let loginDTO: LoginDTO = new LoginDTO(
      this.loginForm.controls.username.value,
      this.loginForm.controls.password.value
    );
    this.loginService.login(loginDTO).subscribe(
      (data) => {
        this.loginService.setToken(data.id_token);
        this.router.navigate(["/patient/all"]);
      },
      (error) => {
        this.catchError(error);
        this.loading = false;
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   */
  catchError(error: number): void {
    if (error && error === 401) {
      this.error =
        "Le nom d'utilisateur et le mot de passe ne correspondent pas.";
    } else {
      this.error = "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
  }
}
