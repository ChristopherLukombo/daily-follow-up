import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { LoginDTO } from "src/app/models/dto/loginDTO";
import { LoginService } from "src/app/services/login/login.service";
import { Router } from "@angular/router";
import { faUser, faKey } from "@fortawesome/free-solid-svg-icons";
import { TypeMessage } from "src/app/models/utils/message-enum";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  userLogo = faUser;
  passwordLogo = faKey;

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
    if (this.loginForm.invalid) return;
    this.loading = true;
    let loginDTO: LoginDTO = new LoginDTO(
      this.f.username.value,
      this.f.password.value
    );
    this.loginService.login(loginDTO).subscribe(
      (data) => {
        this.loginService.setJwtToken(data);
        this.router.navigate(["/food/menu/currents"]);
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
      this.error = TypeMessage.PSEUDO_OR_PASSWORD_INCORRECT;
    } else {
      this.error = TypeMessage.AN_ERROR_OCCURED;
    }
  }
  /**
   * Suppression des msg d'erreurs
   */
  cleanErrorMessages(): void {
    this.error = undefined;
  }
}
