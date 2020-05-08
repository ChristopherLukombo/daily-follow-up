import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { FormPatientEditComponent } from "./form-patient-edit.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ToastrModule, ToastrService } from "ngx-toastr";
import { RouterTestingModule } from "@angular/router/testing";

describe("FormPatientEditComponent", () => {
  let component: FormPatientEditComponent;
  let fixture: ComponentFixture<FormPatientEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        ToastrModule.forRoot(),
        RouterTestingModule,
      ],
      declarations: [FormPatientEditComponent],
      providers: [ToastrService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormPatientEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
