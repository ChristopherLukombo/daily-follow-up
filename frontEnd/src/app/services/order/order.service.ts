import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable, throwError } from "rxjs";
import { Order } from "src/app/models/patient/order";
import { catchError } from "rxjs/operators";
import { OrderDTO } from "src/app/models/dto/patient/orderDTO";
import { OrderCustomInfos } from "src/app/models/utils/order-custom-infos";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const ORDERS_URL = environment.appRootUrl + "/api/orders";

@Injectable({
  providedIn: "root",
})
export class OrderService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne toutes les commandes de la clinique en fonction de la date donnée
   * @param date
   * @returns une liste de commandes du jour donnée
   */
  getOrdersByDate(date: string): Observable<Order[]> {
    return this.http
      .get<Order[]>(ORDERS_URL + `?selectedDate=${date}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne une commande en fonction de son id
   * @param id
   * @returns une commande
   */
  getOrder(id: number): Observable<Order> {
    return this.http
      .get<Order>(ORDERS_URL + `/${id}`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Créer une commande
   * @param orderDTO
   * @returns la commande créé
   */
  createOrder(orderDTO: OrderDTO): Observable<Order> {
    return this.http
      .post<Order>(ORDERS_URL, JSON.stringify(orderDTO), httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Met à jour une commande
   * @param orderDTO
   * @returns la commande mis à jour
   */
  updateOrder(orderDTO: OrderDTO): Observable<Order> {
    return this.http
      .put<Order>(ORDERS_URL, JSON.stringify(orderDTO), httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Sauvegarde en local une date-moment d'une commande afin de les réutiliser plus tard
   * @param orderCustomInfos
   */
  storeOrderInfosToLocal(orderCustomInfos: OrderCustomInfos): void {
    let replace = (key, value) => (typeof value === "undefined" ? null : value);
    localStorage.setItem(
      "infos_order",
      JSON.stringify(orderCustomInfos, replace)
    );
  }

  /**
   * Supprime les infos de la commande sauvegardé en local
   */
  removeOrderInfosFromLocal(): void {
    localStorage.removeItem("infos_order");
  }

  /**
   * Retourne les infos de la la commande sauvegardé en local
   * @returns les infos de la commande
   */
  getOrderInfosFromLocal(): OrderCustomInfos {
    let item = localStorage.getItem("infos_order");
    return <OrderCustomInfos>JSON.parse(item);
  }

  /**
   * Supprime une commande en fonction de son id
   * @param id
   * @returns HttpResponse<Object>
   */
  deleteOrder(id: number): Observable<HttpResponse<Object>> {
    return this.http
      .delete<HttpResponse<Object>>(ORDERS_URL + `/${id}`, httpOptions)
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
