import { TestBed } from "@angular/core/testing";

import { LoginService } from "./login.service";
import { LoginDTO } from "src/app/models/dto/loginDTO";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("LoginService", () => {
  let service: LoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(LoginService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });

  it("#login should return token of user", (done: DoneFn) => {
    let token = "mLOZhh53QHNkxM_hwE27^rW@NIt{I6";
    let loginDTO = new LoginDTO("username", "password");
    service.login(loginDTO).subscribe((value) => {
      expect(value).toBe(token);
      done();
    });
  });

  it("#login should return token of user", (done: DoneFn) => {
    const loginServiceSpy = jasmine.createSpyObj("LoginService", ["login"]);
    const expected = "mLOZhh53QHNkxM_hwE27^rW@NIt{I6";
    loginServiceSpy.login.and.return(expected);
    let loginDTO = new LoginDTO("username", "password");
    service.login(loginDTO).subscribe((value) => {
      expect(value).toBe(expected);
      done();
    });
  });
});
