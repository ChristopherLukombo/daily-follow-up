import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { LoginDTO } from "src/app/models/dto/loginDTO";
import { LoginService } from "src/app/services/login/login.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  loginForm: FormGroup;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    const target = {
      username: ["", [Validators.required, Validators.maxLength(50)]],
      password: ["", [Validators.required, Validators.maxLength(50)]]
    };
    this.loginForm = this.formBuilder.group(target);
  }

  onLogin(): void {
    this.error = undefined;
    let loginDTO: LoginDTO = new LoginDTO(
      this.loginForm.controls.username.value,
      this.loginForm.controls.password.value
    );
    console.log(
      "username: " + loginDTO.username + ", password: " + loginDTO.password
    );
    this.loginService.login(loginDTO).subscribe(
      data => {
        console.log(JSON.stringify(data));
      },
      error => {
        this.error = error;
      }
    );
  }
}
