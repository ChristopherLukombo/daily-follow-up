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
import { ContentDTO } from "src/app/models/dto/food/contentDTO";
import { Content } from "src/app/models/food/content";
import { MenuDTO } from "src/app/models/dto/food/menuDTO";
import { Menu } from "src/app/models/food/menu";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const DIETS_URL = environment.appRootUrl + "/api/diets";
const TEXTURES_URL = environment.appRootUrl + "/api/textures";
const CONTENTS_URL = environment.appRootUrl + "/api/contents";
const MENUS_URL = environment.appRootUrl + "/api/menus";

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
   * Retourne tout plats de la clinique
   * @returns une liste de plat
   */
  getAllContents(): Observable<Content[]> {
    return this.http
      .get<Content[]>(CONTENTS_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Créer plusieurs plats
   * @param listContentsDTO la liste de plat
   * @returns les plats ayant étés crées
   */
  createAllContent(listContentsDTO: ContentDTO[]): Observable<Content[]> {
    let body = { contents: listContentsDTO };
    return this.http
      .post<Content[]>(
        CONTENTS_URL + `/contentList`,
        JSON.stringify(body),
        httpOptions
      )
      .pipe(catchError(this.handleError));
  }

  /**
   * Créer un menu
   * @param menuDTO le menu
   * @returns le menu crée
   */
  createMenu(menuDTO: MenuDTO): Observable<Menu> {
    return this.http
      .post<Menu>(MENUS_URL, JSON.stringify(menuDTO), httpOptions)
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
