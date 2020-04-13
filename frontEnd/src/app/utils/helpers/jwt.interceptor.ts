import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from "@angular/common/http";
import { LoginService } from "src/app/services/login/login.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class JwtInterceptor implements HttpInterceptor {
  constructor(private loginService: LoginService) {}

  requestNeedAuthorization(url: string): Boolean {
    if (url.toLowerCase().includes("login")) {
      return false;
    }
    return true;
  }

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (!this.requestNeedAuthorization(request.url)) {
      return;
    }

    if (
      this.loginService.isAuthenticated() &&
      !this.loginService.isTokenExpired()
    ) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.loginService.getToken()}`,
        },
      });
    }

    return next.handle(request);
  }
}
