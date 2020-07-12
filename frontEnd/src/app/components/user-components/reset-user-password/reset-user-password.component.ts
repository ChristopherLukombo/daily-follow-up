import { Component, OnInit } from "@angular/core";
import { faUser, faKey } from "@fortawesome/free-solid-svg-icons";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { UserService } from "src/app/services/user/user.service";
import { ToastrService } from "ngx-toastr";
import { HttpErrorResponse } from "@angular/common/http";
import { LoginService } from "src/app/services/login/login.service";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-reset-user-password",
  templateUrl: "./reset-user-password.component.html",
  styleUrls: ["./reset-user-password.component.scss"],
})
export class ResetUserPasswordComponent implements OnInit {
  userLogo = faUser;
  passwordLogo = faKey;

  form: FormGroup;
  submitted: Boolean = false;
  error: string;
  loading: Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private loginService: LoginService,
    private router: Router,
    private toastrService: ToastrService
  ) { }

  ngOnInit(): void {
    this.createForm(
      this.loginService.getTokenId(),
      this.loginService.getTokenPseudo()
    );
  }

  createForm(id: number, pseudo: string) {
    if (!id || !pseudo) return;
    const target = {
      id: [id, Validators.required],
      username: [pseudo, Validators.required],
      password: [
        null,
        [
          Validators.required,
          Validators.pattern(
            "(?=^.{8,15}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*"
          ),
        ],
      ],
      confirm: [null, [Validators.required]],
    };
    this.form = this.formBuilder.group(target);
  }

  get f() {
    return this.form.controls;
  }

  onSubmit(): void {
    this.error = null;
    this.submitted = true;
    if (this.form.invalid) return;
    if (this.f.password.value !== this.f.confirm.value) {
      this.error = TypeMessage.PASSWORDS_DOES_NOT_MATCHS;
      return;
    }
    this.loading = true;
    this.userService
      .updatePassword(this.f.id.value, this.f.password.value)
      .subscribe(
        (data) => {
          this.loginService.changedPassword(true);
          this.toastrService.success(
            "Votre mot de passe a bien été mis à jour",
            "Bienvenue !"
          );
          this.router.navigate(["/food/menu/currents"]);
        },
        (error) => {
          this.error = this.getCustomError(error);
          this.loading = false;
        }
      );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 500) {
      return error.error.message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
