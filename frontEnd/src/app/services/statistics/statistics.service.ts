import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Patient } from 'src/app/models/patient/patient';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { PatientsByStatus } from 'src/app/models/statistics/patients-by-status';
import { PatientsPerAllergy } from 'src/app/models/statistics/patients-per-allergy';
import { PatientsPerDiet } from 'src/app/models/statistics/patients-per-diet';
import { OrdersPerDay } from 'src/app/models/statistics/orders-per-day';
import { TopTrendyMenu } from 'src/app/models/statistics/top-trendy-menu';

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const STATS_URL = environment.appRootUrl + "/api/stats";

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private http: HttpClient) { }

  /**
 * Retourne le nombre de patients par allergies.
 * @returns le nombre de patients par allergies
 */
  getNumberOfPatientsPerAllergy(): Observable<PatientsPerAllergy[]> {
    return this.http
      .get<PatientsPerAllergy[]>(`${STATS_URL}/patientsPerAllergy`, httpOptions)
      .pipe(catchError(this.handleError));
  }
/**
 *Retourne le nombre de patientes par diet.
 * @returns une liste de patients par diet
 */
  getNumberOfPatientsPerDiet(): Observable<PatientsPerDiet[]> {
    return this.http
      .get<PatientsPerDiet[]>(`${STATS_URL}/patientsPerDiet`, httpOptions)
      .pipe(catchError(this.handleError));
  }

 /**
  * Retourne le nombre de patients par statut.
 * @returns le nombre de patient par statut
 */
  getNumberOfPatientsByStatus(): Observable<PatientsByStatus> {
    return this.http
      .get<PatientsByStatus>(`${STATS_URL}/patientsByStatus`, httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne le nombre de commandes par jour
   */
  getNumberOfOrdersPerDay(): Observable<Map<string, Array<OrdersPerDay>>> {
    return this.http
      .get<unknown>(`${STATS_URL}/ordersPerDay`, httpOptions)
      .pipe(map(this.convertPropertyElementsToMap))
      .pipe(catchError(this.handleError));
  }

  convertPropertyElementsToMap(orderPerStatus: unknown): Map<string, Array<OrdersPerDay>> {
    return new Map(Object.entries(orderPerStatus));
  }

  /**
  * Retourne le nombre de regimé qui renviennent souvent
 * @returns le nombre de régime revenant souvent
 */
getTrendyDiets(): Observable<TopTrendyMenu[]> {
  return this.http
    .get<TopTrendyMenu[]>(`${STATS_URL}/trendyDiets`, httpOptions)
    .pipe(catchError(this.handleError));
}

 /**
  * Retourne le nombre de plats qui renviennent le plus souvent
 * @returns le nombre de plats qui renvienent le plus souvent
 */
getTrendyContents(): Observable<Map<string, number>> {
  return this.http
    .get<Map<string, number>>(`${STATS_URL}/trendyContents`, httpOptions)
    .pipe(map(this.convertPropertyContentsToMap))
    .pipe(catchError(this.handleError));
}

convertPropertyContentsToMap(trendyContents: unknown): Map<string, number> {
  return new Map(Object.entries(trendyContents));
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
