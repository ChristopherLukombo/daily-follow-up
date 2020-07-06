import { Injectable } from "@angular/core";
import {
  HttpHeaders,
  HttpClient,
  HttpErrorResponse,
} from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable, throwError } from "rxjs";
import { Order } from "src/app/models/patient/order";
import { catchError } from "rxjs/operators";

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
   * Retourne toutes les commandes de la clinique
   * @returns une liste de commandes
   */
  getAllOrders(): Observable<Order[]> {
    return this.http
      .get<Order[]>(ORDERS_URL + `?selectedDate=2020-07-01`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne toutes les commandes de la clinique
   * @returns une liste de commandes
   */
  getOrdersByDate(date: string): Observable<Order[]> {
    return this.http
      .get<Order[]>(ORDERS_URL + `?selectedDate=${date}`, httpOptions)
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
