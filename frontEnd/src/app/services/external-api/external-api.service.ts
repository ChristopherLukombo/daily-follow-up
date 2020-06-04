import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { Dish } from "src/app/models/external-api/dish";
import { Content } from "src/app/models/food/content";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const EXTERNAL_API_URL = environment.appRootUrl + "/api/dishes";

@Injectable({
  providedIn: "root",
})
export class ExternalApiService {
  constructor(private http: HttpClient) {}

  /**
   * Recherche de plats depuis la table CIQUAL
   * @param search
   * @returns la liste des plats correspondant
   */
  searchMeals(search: string): Observable<Dish[]> {
    return this.http
      .get<Dish[]>(EXTERNAL_API_URL + `/search?name=${search}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Trouve un plat dans la table CIQUAL
   * @param name
   * @returns le plat correspondant
   */
  matchMeal(name: string): Observable<Dish> {
    return this.http
      .get<Dish>(EXTERNAL_API_URL + `/find?name=${name}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Convertit un plat CIQUAL vers un plat de la clinique
   * @param dish
   * @returns le plat de la clinique
   */
  toContent(dish: Dish): Content {
    return dish as Content;
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
