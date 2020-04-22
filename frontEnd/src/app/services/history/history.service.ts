import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable, throwError } from "rxjs";
import { HistoryPatient } from "src/app/models/history/history-patient";
import { catchError } from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const MANAGE_URL = environment.appRootUrl + "/management/audits";
const HISTORY_PATIENT_URL = MANAGE_URL + "/patient/history";

@Injectable({
  providedIn: "root",
})
export class HistoryService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne l'historique des actions sur un patient
   * @param idPatient
   * @returns l'historique du patient
   */
  getAllPatientHistories(
    idPatient: number,
    size: number,
    page: number = 0
  ): Observable<HttpResponse<Object>> {
    return this.http
      .get<HttpResponse<Object>>(
        HISTORY_PATIENT_URL + `/${idPatient}?page=${page}&size=${size}`,
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
