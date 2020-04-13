import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { LoginComponent } from "./login.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { RouterTestingModule } from "@angular/router/testing";

describe("LoginComponent", () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        RouterTestingModule,
      ],
      declarations: [LoginComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("username field validity required", () => {
    let errors = {};
    let username = component.loginForm.controls["username"];

    // required
    errors = username.errors || {};
    expect(username.valid).toBeFalsy();
    expect(errors["required"]).toBeTruthy();
    // valid
    username.setValue("test_u");
    errors = username.errors || {};
    expect(username.valid).toBeTruthy();
    expect(errors["required"]).toBeFalsy();
  });

  it("password field validity", () => {
    let errors = {};
    let password = component.loginForm.controls["password"];

    // required
    errors = password.errors || {};
    expect(password.valid).toBeFalsy();
    expect(errors["required"]).toBeTruthy();
    // valid
    password.setValue("123456789");
    errors = password.errors || {};
    expect(password.valid).toBeTruthy();
    expect(errors["required"]).toBeFalsy();
  });

  it("login form invalid when empty", () => {
    expect(component.loginForm.valid).toBeFalsy();
  });

  it("login form valid when not empty", () => {
    expect(component.loginForm.valid).toBeFalsy();

    component.loginForm.controls["username"].setValue("test_u");
    component.loginForm.controls["password"].setValue("123456789");
    expect(component.loginForm.valid).toBeTruthy();
  });
});
