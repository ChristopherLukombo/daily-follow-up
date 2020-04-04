import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from "@angular/common/http";
import { throwError, Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { LoginDTO } from "src/app/models/dto/loginDTO";

const httpOptions = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
  }),
};
const ROOT_URL = "http://localhost:8081/api";
const LOGIN_URL = ROOT_URL + "/authenticate";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(private http: HttpClient) {}

  /**
   * Authentification à l'application,
   * Récuperation du token
   * @param loginDTO
   * @return le token
   */
  login(loginDTO: LoginDTO): Observable<any> {
    return this.http
      .post(LOGIN_URL, JSON.stringify(loginDTO), httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Déconnexion à l'application,
   * Destruction du token
   */
  logout(): Observable<any> {
    return new Observable((observer) => {
      localStorage.clear(), observer.complete();
    });
  }

  /**
   * Gestion des erreurs du backend
   * @param error
   */
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error("An error occurred:", error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was : ${error.error.message}`
      );
    }
    return throwError(error.status);
  }
}
