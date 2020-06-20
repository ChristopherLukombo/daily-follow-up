import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable, throwError, BehaviorSubject } from "rxjs";
import { Diet } from "src/app/models/patient/diet";
import { catchError, map } from "rxjs/operators";
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
      .pipe(map((diets) => this.convertElementsToMap(diets)))
      .pipe(catchError(this.handleError));
  }

  /**
   * Convertit les ingrédients caractéristiques des régimes en Map
   * @param diets
   * @returns la liste de régime avec la Map des <ingrédients, (riche en/pauvre en)>
   */
  convertElementsToMap(diets: Diet[]): Diet[] {
    diets.forEach((diet) => {
      diet = this.convertPropertyElementsToMap(diet);
    });
    return diets;
  }

  /**
   * Convertit les ingrédients caractéristiques d'un régime en Map
   * @param diet
   * @returns le régime avec la Map des <ingrédients, (riche en/pauvre en)>
   */
  convertPropertyElementsToMap(diet: Diet): Diet {
    diet.elementsToCheck = new Map(Object.entries(diet.elementsToCheck));
    return diet;
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
   * Retourne tout les menus de la clinique
   * @returns tout les menus
   */
  getAllMenus(): Observable<Menu[]> {
    return this.http
      .get<Menu[]>(MENUS_URL, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Sauvegarde en local un menu afin de le réutiliser plus tard
   * @param menu
   */
  storeMenuToLocal(menu: Menu): void {
    localStorage.setItem("menu", JSON.stringify(menu));
  }

  /**
   * Supprime le menu sauvegardé en local
   */
  removeMenuFromLocal(): void {
    localStorage.removeItem("menu");
  }

  /**
   * Retourne le menu sauvegardé en local
   * @returns le menu
   */
  getMenuFromLocal(): Menu {
    let item = localStorage.getItem("menu");
    return <Menu>JSON.parse(item);
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
