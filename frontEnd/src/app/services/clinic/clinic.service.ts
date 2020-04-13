import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable, throwError } from "rxjs";
import { Room } from "src/app/models/clinic/room";
import { catchError } from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const ROOMS_URL = environment.appRootUrl + "/rooms";

@Injectable({
  providedIn: "root",
})
export class ClinicService {
  constructor(private http: HttpClient) {}

  getAllRooms(): Observable<Room[]> {
    return this.http
      .get<Room[]>(ROOMS_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  getRoom(id: number): Observable<Room> {
    return this.http
      .get<Room>(ROOMS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  getRoomOfPatient(patientId: number): Observable<Room> {
    return this.http
      .get<Room>(ROOMS_URL + `/patient/${patientId}`, httpOptions)
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
