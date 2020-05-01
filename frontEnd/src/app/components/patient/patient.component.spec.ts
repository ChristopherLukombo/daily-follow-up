import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientComponent } from "./patient.component";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ToastrModule, ToastrService } from "ngx-toastr";
import { Patient } from "src/app/models/patient/patient";
import { of, Observable } from "rxjs";
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
      component.patient = new Patient();
      component.patient.state = true;
      component.patient.roomId = 5;
      const mockPatient = new Patient();
      mockPatient.state = false;
      mockPatient.roomId = null;

      spyOn(patientService, "deletePatient").and.returnValue(of(mock));
      spyOn(patientService, "getPatient").and.returnValue(of(mockPatient));
      component.deletePatient();
      fixture.detectChanges();

      expect(component.patient.state).toBeFalse();
      expect(component.patient.roomId).toBeNull();
    });

    it("should not refresh patient property when the request failed", () => {
      let mock: Error;
      component.patient = new Patient();
      component.patient.roomId = 5;
      component.patient.state = true;

      spyOn(patientService, "deletePatient").and.throwError(mock);
      component.deletePatient();
      fixture.detectChanges();

      const expected = 5;
      expect(component.patient.state).toBeTrue();
      expect(component.patient.roomId).toBe(expected);
    });
  });

  describe("#restorePatient", () => {
    it("should refresh patient state when the request succeed", () => {
      let mock: HttpResponse<Object>;
      component.patient = new Patient();
      component.patient.state = false;
      const mockPatient = new Patient();
      mockPatient.state = true;

      spyOn(patientService, "restorePatient").and.returnValue(of(mock));
      spyOn(patientService, "getPatient").and.returnValue(of(mockPatient));
      component.restorePatient();
      fixture.detectChanges();

      expect(component.patient.state).toBeTrue();
    });

    it("should not refresh patient state when the request failed", () => {
      let mock: Error;
      component.patient = new Patient();
      component.patient.state = false;

      spyOn(patientService, "restorePatient").and.throwError(mock);
      component.restorePatient();
      fixture.detectChanges();

      expect(component.patient.state).toBeFalse();
    });
  });
});
