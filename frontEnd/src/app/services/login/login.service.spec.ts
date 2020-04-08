import { TestBed } from "@angular/core/testing";

import { LoginService } from "./login.service";
import { LoginDTO } from "src/app/models/dto/loginDTO";
import {
  HttpClientTestingModule,
  HttpTestingController,
} from "@angular/common/http/testing";
import { environment } from "src/environments/environment";

describe("LoginService", () => {
  let service: LoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoginService],
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  describe("#login", () => {
    it("should return the token if authentification succeed", () => {
      const expected = { token: "mLOZhh53QHNkxM_hwE27^rW@NIt{I6" };

      let loginDTO = new LoginDTO("test_u", "123456789");
      service.login(loginDTO).subscribe((data) => {
        // expect(data.length).toBe(2);
        expect(data.token).toEqual(expected.token);
      });

      const request = httpMock.expectOne(
        `${environment.appRootUrl}/authenticate`
      );
      expect(request.request.method).toBe("POST");
      request.flush(expected);
    });
  });

  describe("#login", () => {
    it("should return error if authentification failed", () => {
      const expected = 403;

      let loginDTO = new LoginDTO("test_uuuu", "123456789");
      service.login(loginDTO).subscribe(
        () => {
          fail("should have failed with the 403 error");
        },
        (error) => {
          expect(error).toEqual(expected);
        }
      );

      const request = httpMock.expectOne(
        `${environment.appRootUrl}/authenticate`
      );
      request.flush("403 error", { status: 403, statusText: "Not Authorized" });
    });
  });
});
