import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable, throwError } from "rxjs";
import { Patient } from "src/app/models/patient/patient";
import { catchError } from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const PATIENTS_URL = environment.appRootUrl + "/patients";

@Injectable({
  providedIn: "root",
})
export class PatientService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne tout les patients de la clinique
   * @return une liste de patients
   */
  getAllPatients(): Observable<Patient[]> {
    return this.http
      .get<Patient[]>(PATIENTS_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne un patient en fonction de son id
   * @param id du patient
   * @return un Patient
   */
  getPatient(id: number): Observable<Patient> {
    return this.http
      .get<Patient>(PATIENTS_URL + `/${id}`, httpOptions)
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
