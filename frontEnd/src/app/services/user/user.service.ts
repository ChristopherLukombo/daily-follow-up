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
import { CaregiverDTO } from "src/app/models/dto/user/caregiverDTO";

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
   * @returns une liste de d'aides-soignants
   */
  getAllCaregivers(): Observable<Caregiver[]> {
    return this.http
      .get<Caregiver[]>(CAREGIVER_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne les aides-soignants d'un étage donné
   * @param number le numero de l'étage
   * @returns une liste d'aides-soignants
   */
  getCaregiversByFloor(number: number): Observable<Caregiver[]> {
    return this.http
      .get<Caregiver[]>(CAREGIVER_URL + `/floor/${number}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Crée un nouvel aide-soignant
   * @param caregiverDTO
   * @returns l'aide-soignant créé
   */
  createCaregiver(caregiverDTO: CaregiverDTO): Observable<Caregiver> {
    return this.http
      .post<Caregiver>(CAREGIVER_URL, JSON.stringify(caregiverDTO), httpOptions)
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
   * Met à jour le mot de passe de l'utilisateur
   * @param userId
   * @param password
   */
  updatePassword(userId: number, password: string): Observable<Object> {
    let dto = { password: password, userId: userId };
    return this.http
      .patch<Object>(USER_URL + "/pass", JSON.stringify(dto), httpOptions)
      .pipe(catchError(this.handleCustomError));
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

  /**
   * Gestion des erreurs du backend
   * @param error
   */
  private handleCustomError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error("An error occurred:", error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was : ${error.error.message}`
      );
    }
    return throwError(error);
  }
}
