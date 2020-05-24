import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { throwError, Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { Caregiver } from "src/app/models/user/caregiver";
import { User } from "src/app/models/user/user";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const CAREGIVER_URL = environment.appRootUrl + "/api/caregivers";
const USER_URL = environment.appRootUrl + "/api/users";

@Injectable({
  providedIn: "root",
})
export class UserService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne tout les utilisateurs de l'application
   * @returns une liste de d'utilisateur
   */
  getAllUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(USER_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne tout les aides-soignats de la clinique
   * @returns une liste de d'aides-soignats
   */
  getAllCaregivers(): Observable<Caregiver[]> {
    return this.http
      .get<Caregiver[]>(CAREGIVER_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne un utilisateur de l'application
   * @param id de l'utilisateur
   * @returns une liste de d'utilisateur
   */
  getUser(id: number): Observable<User> {
    return this.http
      .get<User>(USER_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleError));
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
