import { TestBed } from "@angular/core/testing";

import { PatientService } from "./patient.service";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { environment } from "src/environments/environment";
import { Patient } from "src/app/models/patient/patient";

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
});
