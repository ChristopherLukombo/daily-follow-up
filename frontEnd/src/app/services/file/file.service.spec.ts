import { TestBed } from "@angular/core/testing";
import { FileService } from "./file.service";
import {
  HttpTestingController,
  HttpClientTestingModule,
} from "@angular/common/http/testing";
import { HttpEvent, HttpEventType } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { ResultCsvPatient } from "src/app/models/csv/result-csv-patient";

describe("FileService", () => {
  let service: FileService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [FileService],
    });
    service = TestBed.inject(FileService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  describe("#uploadPatientsFile", () => {
    it("should report the progress of the file upload", (done: DoneFn) => {
      const file = new File(["file"], "file.csv", { type: "text/csv" });
      const expected = 70;
      service.uploadPatientsFile(file).subscribe((data: HttpEvent<any>) => {
        if (data.type === HttpEventType.UploadProgress) {
          expect(Math.round((data.loaded / data.total) * 100)).toEqual(
            expected
          );
        }
        done();
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/patients/import`
      );
      expect(request.request.method).toBe("POST");
      request.event({
        type: HttpEventType.UploadProgress,
        loaded: 7,
        total: 10,
      });
    });

    it("should return result when the file is uploaded", (done: DoneFn) => {
      const file = new File(["file"], "file.csv", { type: "text/csv" });
      const expectedBody = new ResultCsvPatient();
      const expectedStatus = 200;
      service.uploadPatientsFile(file).subscribe((data: HttpEvent<any>) => {
        if (data.type === HttpEventType.Response) {
          expect(data.body).toEqual(expectedBody);
          expect(data.status).toEqual(expectedStatus);
        }
        done();
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/patients/import`
      );
      expect(request.request.method).toBe("POST");
      request.event({
        type: HttpEventType.Response,
        clone: null,
        headers: null,
        status: 200,
        statusText: "OK",
        url: "",
        ok: true,
        body: new ResultCsvPatient(),
      });
    });

    it("should return error when the file is not valid", (done: DoneFn) => {
      const file = new File(["file"], "file.csv", { type: "text/csv" });
      const expected = 422;
      service.uploadPatientsFile(file).subscribe(
        () => {
          done();
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/patients/import`
      );
      expect(request.request.method).toBe("POST");
      request.flush("422 error", {
        status: 422,
        statusText: "Unprocessable entity",
      });
    });

    it("should return error when patients already exists", (done: DoneFn) => {
      const file = new File(["file"], "file.csv", { type: "text/csv" });
      const expected = 409;
      service.uploadPatientsFile(file).subscribe(
        () => {
          done();
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/patients/import`
      );
      expect(request.request.method).toBe("POST");
      request.flush("409 error", {
        status: 409,
        statusText: "Conflict",
      });
    });
  });
});
