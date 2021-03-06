import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable, throwError } from "rxjs";
import { Patient } from "src/app/models/patient/patient";
import { catchError } from "rxjs/operators";
import { PatientDTO } from "src/app/models/dto/patient/patientDTO";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const PATIENTS_URL = environment.appRootUrl + "/api/patients";

@Injectable({
  providedIn: "root",
})
export class PatientService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne tout les patients de la clinique
   * @returns une liste de patients
   */
  getAllPatients(): Observable<Patient[]> {
    return this.http
      .get<Patient[]>(PATIENTS_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne la liste de patient d'un étage
   * @param number le numéro de la chambre
   * @returns une liste de patient
   */
  getPatientsByFloor(number: number): Observable<Patient[]> {
    return this.http
      .get<Patient[]>(PATIENTS_URL + `/floor/${number}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne tout les anciens patients de la clinique
   * @returns une liste d'anciens patients
   */
  getAllFormerPatients(): Observable<Patient[]> {
    return this.http
      .get<Patient[]>(PATIENTS_URL + `/former`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne un patient en fonction de son id
   * @param id du patient
   * @returns un Patient
   */
  getPatient(id: number): Observable<Patient> {
    return this.http
      .get<Patient>(PATIENTS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne un patient en fonction de l'id d'une commande
   * @param orderId de la commande
   * @returns un Patient
   */
  getPatientByOrder(orderId: number): Observable<Patient> {
    return this.http
      .get<Patient>(PATIENTS_URL + `/order/${orderId}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Créer un patient
   * @param patientDTO
   * @returns un Patient
   */
  createPatient(patientDTO: PatientDTO): Observable<Patient> {
    return this.http
      .post<Patient>(PATIENTS_URL, JSON.stringify(patientDTO), httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Met à jour un patient
   * @param patientDTO
   * @returns un Patient
   */
  updatePatient(patientDTO: PatientDTO): Observable<Patient> {
    return this.http
      .put<Patient>(PATIENTS_URL, JSON.stringify(patientDTO), httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Echange une chambre entre 2 patients
   * @param firstPatientId
   * @param secondPatientId
   * @returns HttpResponse<Object>
   */
  changesRoom(
    firstPatientId: number,
    secondPatientId: number
  ): Observable<HttpResponse<Object>> {
    return this.http
      .post<HttpResponse<Object>>(
        PATIENTS_URL + `/changesRoom/${firstPatientId}/${secondPatientId}`,
        httpOptions
      )
      .pipe(catchError(this.handleError));
  }

  /**
   * Supprime un patient en fonction de son id
   * @param id
   * @returns HttpResponse<Object>
   */
  deletePatient(id: number): Observable<HttpResponse<Object>> {
    return this.http
      .delete<HttpResponse<Object>>(PATIENTS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Recréer un patient en fonction de son id
   * @param id
   * @returns HttpResponse<Object>
   */
  restorePatient(id: number): Observable<HttpResponse<Object>> {
    return this.http
      .get<HttpResponse<Object>>(
        PATIENTS_URL + `/reactivate/${id}`,
        httpOptions
      )
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
