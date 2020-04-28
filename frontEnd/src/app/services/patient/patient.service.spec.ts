import { TestBed } from "@angular/core/testing";

import { PatientService } from "./patient.service";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { environment } from "src/environments/environment";
import { Patient } from "src/app/models/patient/patient";
import { PatientDTO } from "src/app/models/dto/patientDTO";
import { HttpResponse } from "@angular/common/http";

describe("PatientService", () => {
  let service: PatientService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PatientService],
    });
    service = TestBed.inject(PatientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  describe("#getAllPatients", () => {
    it("should return all patients", () => {
      const expected = [new Patient(), new Patient()];
      service.getAllPatients().subscribe((data) => {
        expect(data.length).toBe(2);
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return emtpy list if there is no patients", () => {
      const expected = [];
      service.getAllPatients().subscribe((data) => {
        expect(data).toEqual(expected);
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return error if user not authorized", () => {
      const expected = 403;
      service.getAllPatients().subscribe(
        () => {
          fail("should have failed with the 403 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients`
      );
      request.flush("403 error", { status: 403, statusText: "Not Authorized" });
    });
  });

  describe("#getPatient", () => {
    it("should return a patient", () => {
      const id = 1;
      const expected = new Patient();
      service.getPatient(id).subscribe((data) => {
        expect(Object.keys(data).sort()).toEqual(Object.keys(expected).sort());
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return null if patient does not exist", () => {
      const id = 2;
      const expected = null;
      service.getPatient(id).subscribe((data) => {
        expect(data).toEqual(expected);
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return error if user not authorized", () => {
      const id = 3;
      const expected = 403;
      service.getPatient(id).subscribe(
        () => {
          fail("should have failed with the 403 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/${id}`
      );
      request.flush("403 error", { status: 403, statusText: "Not Authorized" });
    });
  });

  describe("#updatePatient", () => {
    it("should the updated patient", () => {
      const patientToUpdate = new Patient();
      const dto = new PatientDTO(
        patientToUpdate.id,
        patientToUpdate.firstName,
        patientToUpdate.lastName,
        patientToUpdate.email,
        patientToUpdate.situation,
        patientToUpdate.dateOfBirth,
        patientToUpdate.address,
        patientToUpdate.phoneNumber,
        patientToUpdate.mobilePhone,
        patientToUpdate.job,
        patientToUpdate.bloodGroup,
        patientToUpdate.height,
        patientToUpdate.weight,
        patientToUpdate.sex,
        patientToUpdate.state,
        patientToUpdate.texture,
        patientToUpdate.diets,
        patientToUpdate.allergies,
        patientToUpdate.orders,
        patientToUpdate.comment,
        patientToUpdate.roomId
      );
      const expected = new Patient();
      service.updatePatient(dto).subscribe((data) => {
        expect(Object.keys(data).sort()).toEqual(Object.keys(expected).sort());
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients`
      );
      expect(request.request.method).toBe("PUT");
      request.flush(expected);
    });

    it("should return error if user not authorized", () => {
      const patientToUpdate = new Patient();
      const dto = new PatientDTO(
        patientToUpdate.id,
        patientToUpdate.firstName,
        patientToUpdate.lastName,
        patientToUpdate.email,
        patientToUpdate.situation,
        patientToUpdate.dateOfBirth,
        patientToUpdate.address,
        patientToUpdate.phoneNumber,
        patientToUpdate.mobilePhone,
        patientToUpdate.job,
        patientToUpdate.bloodGroup,
        patientToUpdate.height,
        patientToUpdate.weight,
        patientToUpdate.sex,
        patientToUpdate.state,
        patientToUpdate.texture,
        patientToUpdate.diets,
        patientToUpdate.allergies,
        patientToUpdate.orders,
        patientToUpdate.comment,
        patientToUpdate.roomId
      );
      const expected = 401;
      service.updatePatient(dto).subscribe(
        () => {
          fail("should have failed with the 401 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients`
      );
      request.flush("401 error", { status: 401, statusText: "Not Authorized" });
    });
  });

  describe("#deletePatient", () => {
    it("should return ok if the patient was successfully deleted", () => {
      const id = 1;
      const expected = "200 OK";
      service.deletePatient(id).subscribe((data) => {
        expect(data.toString()).toBe(expected);
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/${id}`
      );
      expect(request.request.method).toBe("DELETE");
      request.flush("200 OK", { status: 200, statusText: "OK" });
    });

    it("should return error if user not authorized", () => {
      const id = 3;
      const expected = 401;
      service.deletePatient(id).subscribe(
        () => {
          fail("should have failed with the 401 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/${id}`
      );
      expect(request.request.method).toBe("DELETE");
      request.flush("401 error", { status: 401, statusText: "Not Authorized" });
    });
  });

  describe("#restorePatient", () => {
    it("should return ok if the patient was successfully recreated", () => {
      const id = 1;
      const expected = "200 OK";
      service.restorePatient(id).subscribe((data) => {
        expect(data.toString()).toBe(expected);
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/reactivate/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush("200 OK", { status: 200, statusText: "OK" });
    });

    it("should return error if user not authorized", () => {
      const id = 3;
      const expected = 401;
      service.restorePatient(id).subscribe(
        () => {
          fail("should have failed with the 401 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/patients/reactivate/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush("401 error", { status: 401, statusText: "Not Authorized" });
    });
  });
});
