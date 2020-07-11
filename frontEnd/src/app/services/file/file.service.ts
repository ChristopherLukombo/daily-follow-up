import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpErrorResponse,
  HttpEvent,
  HttpHeaders,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { throwError, Observable } from "rxjs";
import { catchError } from "rxjs/operators";

const PATIENTS_URL = environment.appRootUrl + "/api/patients";
const CONTENTS_URL = environment.appRootUrl + "/api/contents";
const ORDERS_URL = environment.appRootUrl + "/api/orders";

@Injectable({
  providedIn: "root",
})
export class FileService {
  constructor(private http: HttpClient) {}

  /**
   * Creation des patients à partir d'un fichier csv
   * @param file
   * @returns la progression de l'upload puis la liste des patients crées et mis à jour
   */
  uploadPatientsFile(file: File): Observable<HttpEvent<any>> {
    let data: FormData = new FormData();
    data.append("inputfile", file);
    return this.http
      .post<HttpEvent<any>>(PATIENTS_URL + "/import", data, {
        reportProgress: true,
        observe: "events",
      })
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne la photo d'un plat
   * @param id
   * @returns le Blob de la photo
   */
  getContentPicture(id: number): Observable<Blob> {
    let headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    return this.http
      .get(CONTENTS_URL + `/picture/${id}`, { headers, responseType: "blob" })
      .pipe(catchError(this.handleError));
  }

  /**
   * Permet d'upload la photo d'un plat
   * @param picture la photo du plat
   * @param id l'id du Content
   * @returns le résultat
   */
  uploadContentPicture(picture: File, id: number): Observable<string> {
    let data: FormData = new FormData();
    data.append("file", picture);
    return this.http
      .post(CONTENTS_URL + `/picture/${id}`, data, {
        responseType: "text",
      })
      .pipe(catchError(this.handleError));
  }

  /**
   * Récupères les coupons pdf du récapitulatif des commandes d'un jour-moment donnée
   * @param date
   * @param moment
   * @returns le Blob du pdf
   */
  getCouponsOfTheDate(date: string, moment: string): Observable<Blob> {
    let headers = new HttpHeaders({
      "Content-Type": "application/json",
    });
    return this.http
      .get(ORDERS_URL + `/coupons?momentName=${moment}&selectedDate=${date}`, {
        headers,
        responseType: "blob",
      })
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
