import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientComponent } from "./patient.component";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ToastrModule, ToastrService } from "ngx-toastr";
import { Patient } from "src/app/models/patient/patient";
import { of } from "rxjs";
import { PatientService } from "src/app/services/patient/patient.service";
import { HttpResponse } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

describe("PatientComponent", () => {
  let component: PatientComponent;
  let fixture: ComponentFixture<PatientComponent>;
  let patientService: PatientService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      declarations: [PatientComponent],
      providers: [ToastrService, PatientService],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientComponent);
    patientService = TestBed.inject(PatientService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  describe("#deletePatient", () => {
    it("should refresh patient property when the request succeed", () => {
      let mock: HttpResponse<Object>;
      const patient = new Patient();
      patient.roomId = 5;
      patient.state = true;
      component.patient = patient;

      spyOn(patientService, "deletePatient").and.returnValue(of(mock));
      component.deletePatient();
      fixture.detectChanges();

      expect(component.patient.state).toBeFalse();
      expect(component.patient.roomId).toBeNull();
    });

    it("should not refresh patient property when the request failed", () => {
      let mock: Error;
      const patient = new Patient();
      patient.roomId = 5;
      patient.state = true;
      component.patient = patient;

      spyOn(patientService, "deletePatient").and.throwError(mock);
      component.deletePatient();
      fixture.detectChanges();

      const expected = patient.roomId;
      expect(component.patient.state).toBeTrue();
      expect(component.patient.roomId).toBe(expected);
    });
  });

  describe("#restorePatient", () => {
    it("should refresh patient state when the request succeed", () => {
      let mock: HttpResponse<Object>;
      const patient = new Patient();
      patient.state = false;
      component.patient = patient;

      spyOn(patientService, "restorePatient").and.returnValue(of(mock));
      component.restorePatient();
      fixture.detectChanges();

      expect(component.patient.state).toBeTrue();
    });

    it("should not refresh patient state when the request failed", () => {
      let mock: Error;
      const patient = new Patient();
      patient.state = false;
      component.patient = patient;

      spyOn(patientService, "restorePatient").and.throwError(mock);
      component.restorePatient();
      fixture.detectChanges();

      expect(component.patient.state).toBeFalse();
    });
  });
});
