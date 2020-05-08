import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpEvent } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { throwError, Observable } from "rxjs";
import { catchError } from "rxjs/operators";

const PATIENTS_URL = environment.appRootUrl + "/api/patients";

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
