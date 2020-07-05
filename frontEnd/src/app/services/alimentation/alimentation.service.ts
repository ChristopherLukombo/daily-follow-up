import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from "@angular/common/http";
import { Observable, throwError, BehaviorSubject } from "rxjs";
import { Diet } from "src/app/models/patient/diet";
import { catchError, map } from "rxjs/operators";
import { Texture } from "src/app/models/food/texture";
import { ContentDTO } from "src/app/models/dto/food/contentDTO";
import { Content } from "src/app/models/food/content";
import { MenuDTO } from "src/app/models/dto/food/menuDTO";
import { Menu } from "src/app/models/food/menu";
import { DietDTO } from "src/app/models/dto/patient/dietDTO";

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
   * Retourne un régime en fonction de son id
   * @param id
   * @returns Le Regime
   */
  getDiet(id: number): Observable<Diet> {
    return this.http
      .get<Diet>(DIETS_URL + `/${id}`, httpOptions)
      .pipe(map((diet) => this.convertPropertyElementsToMap(diet)))
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
    if (!diet) return null;
    diet.elementsToCheck = new Map(Object.entries(diet.elementsToCheck));
    return diet;
  }

  /**
   * Convertit la Map d'ingrédients caractéristiques d'un régime en objet
   * @param dietDTO
   * @returns le nouvel objet Diet JSONifié
   */
  stringifyDiet(dietDTO: DietDTO): string {
    let jsonObject = {};
    dietDTO.elementsToCheck.forEach((value, key) => {
      jsonObject[key] = value;
    });
    let mapToObject = (key, value) =>
      value instanceof Map ? jsonObject : value;
    return JSON.stringify(dietDTO, mapToObject);
  }

  /**
   * Crée un régime à disposition des plats et des patients
   * @param dietDTO
   * @returns la Diet crée
   */
  createDiet(dietDTO: DietDTO): Observable<Diet> {
    return this.http
      .post<Diet>(DIETS_URL, this.stringifyDiet(dietDTO), httpOptions)
      .pipe(catchError(this.handleCustomError));
  }

  /**
   * Modifie un régime
   * @param dietDTO
   * @returns la Diet modifié
   */
  updateDiet(dietDTO: DietDTO): Observable<Diet> {
    return this.http
      .put<Diet>(DIETS_URL, this.stringifyDiet(dietDTO), httpOptions)
      .pipe(catchError(this.handleCustomError));
  }

  /**
   * Supprime un régime en fonction de son id
   * @param id
   * @returns HttpResponse<Object>
   */
  deleteDiet(id: number): Observable<HttpResponse<Object>> {
    return this.http
      .delete<HttpResponse<Object>>(DIETS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleCustomError));
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
   * Retourne un plat en fonction de son id
   * @param id
   * @returns un Content
   */
  getContent(id: number): Observable<Content> {
    return this.http
      .get<Content>(CONTENTS_URL + `/${id}`, httpOptions)
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
      .pipe(catchError(this.handleCustomError));
  }

  /**
   * Met à jour un plat
   * @param contentDTO
   * @returns le Content mis à jour
   */
  updateContent(contentDTO: ContentDTO): Observable<Content> {
    return this.http
      .put<Content>(CONTENTS_URL, JSON.stringify(contentDTO), httpOptions)
      .pipe(catchError(this.handleCustomError));
  }

  /**
   * Supprime un plat en fonction de son id
   * @param id
   * @returns HttpResponse<Object>
   */
  deleteContent(id: number): Observable<HttpResponse<Object>> {
    return this.http
      .delete<HttpResponse<Object>>(CONTENTS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleCustomError));
  }

  /**
   * Supprime plusieurs plats
   * @param contents les plats à supprimer
   * @returns HttpResponse<Object>
   */
  deleteManyContents(contents: Content[]): Observable<HttpResponse<Object>> {
    let ids: number[] = contents.map((content) => content.id);
    const options = {
      headers: new HttpHeaders({
        "Content-Type": "application/json",
      }),
      body: ids,
    };
    return this.http
      .delete<HttpResponse<Object>>(CONTENTS_URL, options)
      .pipe(catchError(this.handleCustomError));
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
   * Retourne un menu en fonction de son Id
   * @param id
   * @returns Le menu
   */
  getMenu(id: number): Observable<Menu> {
    return this.http
      .get<Menu>(MENUS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne tout les futurs menus de la clinique
   * @returns les futurs menus
   */
  getFutureMenus(): Observable<Menu[]> {
    return this.http
      .get<Menu[]>(MENUS_URL + `/future`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne tout les menus de la clinique dans la périod en cours
   * @returns les menus de la période en cours
   */
  getCurrentsMenus(): Observable<Menu[]> {
    return this.http
      .get<Menu[]>(MENUS_URL + `/current`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne tout les  anciens menus de la clinique
   * @returns les anciens menus
   */
  getOldMenus(): Observable<Menu[]> {
    return this.http
      .get<Menu[]>(MENUS_URL + `/past`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Sauvegarde en local un menu afin de le réutiliser plus tard
   * @param menu
   */
  storeMenuToLocal(menu: Menu): void {
    let replace = (key, value) => (typeof value === "undefined" ? null : value);
    localStorage.setItem("menu", JSON.stringify(menu, replace));
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
   * Met à jour un menu
   * @param menuDTO le menu à mettre à jour
   * @returns le menu mis à jour
   */
  updateMenu(menuDTO: MenuDTO): Observable<Menu> {
    return this.http
      .put<Menu>(MENUS_URL, JSON.stringify(menuDTO), httpOptions)
      .pipe(catchError(this.handleCustomError));
  }

  /**
   * Supprime un menu de la clinique
   * @param id
   * @returns HttpResponse<Object>
   */
  deleteMenu(id: number): Observable<HttpResponse<Object>> {
    return this.http
      .delete<HttpResponse<Object>>(MENUS_URL + `/${id}`, httpOptions)
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
