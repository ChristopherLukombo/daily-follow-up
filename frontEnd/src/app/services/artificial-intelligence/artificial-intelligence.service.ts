import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError, map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { AIResult } from "src/app/models/utils/ai-result";

const AI_MODEL_API_URL = environment.appAIUrl;

@Injectable({
  providedIn: "root",
})
export class ArtificialIntelligenceService {
  constructor(private http: HttpClient) {}

  /**
   * Retourne les images possible d'un plat passé en entrée
   * @param name
   * @returns une liste de Blob
   */
  getOutputPicturesFromModel(name: string): Observable<Blob[]> {
    return this.http
      .get<AIResult>(AI_MODEL_API_URL + `/contentPicture?name=${name}`)
      .pipe(map((result) => this.extractDatasFromResult(result.datas)))
      .pipe(catchError(this.handleError));
  }

  /**
   * Retourne une liste de d'image en fonction des base64 passés en paramètres
   * @param results
   * @returns une liste de Blob
   */
  extractDatasFromResult(results: string[]): Blob[] {
    let datas: Blob[] = [];
    results.forEach((data) => {
      datas.push(this.convertBase64ToBlob(data));
    });
    return datas;
  }

  /**
   * Convertit une chaine en base64 en Blob
   * @param data
   * @returns le Blob
   */
  convertBase64ToBlob(data: string): Blob {
    const byteCharacters = atob(data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: "image/png" });
    return blob;
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
