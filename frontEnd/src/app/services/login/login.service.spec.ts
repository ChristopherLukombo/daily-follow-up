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
    let store = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
      removeItem: (key: string) => {
        delete store[key];
      },
      clear: () => {
        store = {};
      },
    };
    spyOn(localStorage, "getItem").and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, "setItem").and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, "removeItem").and.callFake(mockLocalStorage.removeItem);
    spyOn(localStorage, "clear").and.callFake(mockLocalStorage.clear);
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

  describe("#logout", () => {
    it("should clear the local storage when logout", () => {
      const token = "mLOZhh53QHNkxM_hwE27^rW@NIt{I6";
      localStorage.setItem("token", token);
      service.logout().subscribe();
      expect(localStorage.getItem("token")).toBeNull();
    });
  });

  describe("#isAuthenticated", () => {
    it("should return true if the user is authenticated", () => {
      const token = "mLOZhh53QHNkxM_hwE27^rW@NIt{I6";
      localStorage.setItem("token", token);
      const expected = true;
      const actual = service.isAuthenticated();
      expect(actual).toEqual(expected);
    });

    it("should return false if the user is not authenticated", () => {
      const expected = false;
      const actual = service.isAuthenticated();
      expect(actual).toEqual(expected);
    });
  });
});
