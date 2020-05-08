import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { FormPatientAddComponent } from "./form-patient-add.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ToastrService, ToastrModule } from "ngx-toastr";
import { RouterTestingModule } from "@angular/router/testing";

describe("FormPatientAddComponent", () => {
  let component: FormPatientAddComponent;
  let fixture: ComponentFixture<FormPatientAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        ToastrModule.forRoot(),
        RouterTestingModule,
      ],
      declarations: [FormPatientAddComponent],
      providers: [ToastrService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormPatientAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
