import { Injectable } from "@angular/core";
import { Router, CanActivate } from "@angular/router";
import { LoginService } from "src/app/services/login/login.service";

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private loginService: LoginService) {}

  canActivate() {
    if (
      this.loginService.isAuthenticated() &&
      !this.loginService.isTokenExpired()
    ) {
      // have to reset password
      if (!this.loginService.hasChangedPassword()) {
        this.router.navigate(["/reset/password"], {
          queryParams: {
            id: this.loginService.getTokenId(),
            pseudo: this.loginService.getTokenPseudo(),
          },
        });
        return false;
      }
      // authorised so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(["/login"]);
    return false;
  }
}
