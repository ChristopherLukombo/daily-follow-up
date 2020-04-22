import { TestBed } from "@angular/core/testing";

import { ClinicService } from "./clinic.service";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { Room } from "src/app/models/clinic/room";
import { environment } from "src/environments/environment";

describe("ClinicService", () => {
  let service: ClinicService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClinicService],
    });
    service = TestBed.inject(ClinicService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  describe("#getAllRooms", () => {
    it("should return all rooms", () => {
      const expected = [new Room(), new Room()];
      service.getAllRooms().subscribe((data) => {
        expect(data.length).toBe(2);
      });
      const request = httpMock.expectOne(`${environment.appRootUrl}/api/rooms`);
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return emtpy list if there is no rooms", () => {
      const expected = [];
      service.getAllRooms().subscribe((data) => {
        expect(data).toEqual(expected);
      });
      const request = httpMock.expectOne(`${environment.appRootUrl}/api/rooms`);
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return error if user not authorized", () => {
      const expected = 403;
      service.getAllRooms().subscribe(
        () => {
          fail("should have failed with the 403 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(`${environment.appRootUrl}/api/rooms`);
      request.flush("403 error", { status: 403, statusText: "Not Authorized" });
    });
  });

  describe("#getRoom", () => {
    it("should return a room", () => {
      const id = 1;
      const expected = new Room();
      service.getRoom(id).subscribe((data) => {
        expect(Object.keys(data).sort()).toEqual(Object.keys(expected).sort());
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/rooms/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return error if user not authorized", () => {
      const id = 3;
      const expected = 403;
      service.getRoom(id).subscribe(
        () => {
          fail("should have failed with the 403 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/rooms/${id}`
      );
      request.flush("403 error", { status: 403, statusText: "Not Authorized" });
    });
  });

  describe("#getRoomOfPatient", () => {
    it("should return a room of a patient", () => {
      const id = 1;
      const expected = new Room();
      service.getRoomOfPatient(id).subscribe((data) => {
        expect(Object.keys(data).sort()).toEqual(Object.keys(expected).sort());
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/rooms/patient/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return null if room does not contains the patient", () => {
      const id = 2;
      const expected = null;
      service.getRoomOfPatient(id).subscribe((data) => {
        expect(data).toEqual(expected);
      });
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/rooms/patient/${id}`
      );
      expect(request.request.method).toBe("GET");
      request.flush(expected);
    });

    it("should return error if user not authorized", () => {
      const id = 3;
      const expected = 403;
      service.getRoomOfPatient(id).subscribe(
        () => {
          fail("should have failed with the 403 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );
      const request = httpMock.expectOne(
        `${environment.appRootUrl}/api/rooms/patient/${id}`
      );
      request.flush("403 error", { status: 403, statusText: "Not Authorized" });
    });
  });
});
