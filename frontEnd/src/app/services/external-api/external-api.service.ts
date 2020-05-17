import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from "@angular/common/http";
import { Observable, throwError, BehaviorSubject, Subject, timer } from "rxjs";
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  debounce,
  switchMap,
} from "rxjs/operators";
import { Content } from "src/app/models/food/content";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

const EXTERNAL_API_URL = "https://ciqual.anses.fr/esearch";

@Injectable({
  providedIn: "root",
})
export class ExternalApiService {
  mocks: Content[] = [new Content(), new Content(), new Content()];

  constructor(private http: HttpClient) {}

  // searchMeals(terms: Observable<string>) {
  //   return terms.pipe(debounce(() => timer(4000)))
  //     .pipe(distinctUntilChanged(),
  //     switchMap(term => this.searchMeals(term)));

  // }

  searchMeals(search: string): Observable<Content[]> {
    this.mocks[0].name = "poulet " + search;
    this.mocks[1].name = "veau " + search;
    this.mocks[2].name = "frites " + search;
    let res: BehaviorSubject<Content[]> = new BehaviorSubject(this.mocks);
    return res.asObservable();
  }

  search(name: string): Observable<Content[]> {
    this.mocks[0].name = "poulet";
    this.mocks[1].name = "veau";
    this.mocks[2].name = "frites";
    let res: BehaviorSubject<Content[]> = new BehaviorSubject(this.mocks);
    // return this.http
    //   .get<Content[]>(EXTERNAL_API_URL + `/${name}`, httpOptions)
    //   .pipe(catchError(this.handleError));
    return res.asObservable();
  }

  getMealComposition(name: string): Observable<Content> {
    this.mocks[0].name = name;
    let res: BehaviorSubject<Content> = new BehaviorSubject(this.mocks[0]);
    return res.asObservable();
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
