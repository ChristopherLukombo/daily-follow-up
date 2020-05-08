import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { Diet } from "src/app/models/patient/diet";
import { catchError } from "rxjs/operators";
import { Texture } from "src/app/models/food/texture";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const DIETS_URL = environment.appRootUrl + "/api/diets";
const TEXTURES_URL = environment.appRootUrl + "/api/textures";

@Injectable({
  providedIn: "root",
})
export class AlimentationService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne tout les régimes disponibles de la clinique
   * @returns une liste de régime
   */
  getAllDiets(): Observable<Diet[]> {
    return this.http
      .get<Diet[]>(DIETS_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne tout les textures disponibles de la clinique
   * @returns une liste de texture
   */
  getAllTextures(): Observable<Texture[]> {
    return this.http
      .get<Texture[]>(TEXTURES_URL, httpOptions)
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
