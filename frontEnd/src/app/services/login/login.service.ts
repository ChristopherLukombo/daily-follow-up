import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from "@angular/common/http";
import { throwError, Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { LoginDTO } from "src/app/models/dto/loginDTO";
import { environment } from "src/environments/environment";
import * as jwt_decode from "jwt-decode";
import { JwtToken } from "src/app/models/user/jwt-token";
import { Role } from "src/app/models/user/role-enum";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};
const LOGIN_URL = environment.appRootUrl + "/api/authenticate";

@Injectable({
  providedIn: "root",
})
export class LoginService {
  constructor(private http: HttpClient) {}

  /**
   * Authentification à l'application,
   * Récuperation du token
   * @param loginDTO
   * @return le token
   */
  login(loginDTO: LoginDTO): Observable<JwtToken> {
    return this.http
      .post<JwtToken>(LOGIN_URL, JSON.stringify(loginDTO), httpOptions)
      .pipe(catchError(this.handleError));
  }

  /**
   * Déconnexion à l'application,
   * Destruction du token
   */
  logout(): Observable<any> {
    return new Observable((observer) => {
      localStorage.clear(), observer.complete();
    });
  }

  /**
   * Check si l'utilisateur est authentifié
   * @return true ou false
   */
  isAuthenticated(): boolean {
    return localStorage.getItem("token") ? true : false;
  }

  /**
   * Sauvegarde le token d'authentification de l'utilisateur en local
   * @param token
   */
  setJwtToken(token: JwtToken): void {
    this.setToken(token.id_token);
    this.changedPassword(token.has_changed_password);
  }

  /**
   * Permet de savoir si un utilisateur a changé ou non son mot de passe
   */
  hasChangedPassword(): boolean {
    let hasChanged: boolean = JSON.parse(
      localStorage.getItem("has_changed_password")
    );
    return hasChanged === true;
  }

  /**
   * Modifie la valeur qui permet de savoir si un utilisateur doit modifier son mot de passe ou non
   * @param value
   */
  changedPassword(value: boolean): void {
    localStorage.setItem("has_changed_password", JSON.stringify(value));
  }

  /**
   * Token utilisateur
   */
  getToken(): string {
    return localStorage.getItem("token");
  }

  /**
   * Token utilisateur
   * @param token
   */
  setToken(token: string): void {
    localStorage.setItem("token", token);
  }

  /**
   * Permet de récupérer le pseudo de l'utilisateur connecté
   */
  getTokenPseudo(): string {
    if (this.isAuthenticated() == false) return null;
    const token = this.getToken();
    const decoded = jwt_decode(token);
    return decoded.sub == undefined ? null : decoded.sub;
  }

  /**
   * Permet de récupérer le role de l'utilisateur connecté
   */
  getTokenRole(): string {
    if (this.isAuthenticated() == false) return null;
    const token = this.getToken();
    const decoded = jwt_decode(token);
    return decoded.auth == undefined ? null : decoded.auth;
  }

  /**
   * Permet de savoir si un utilisateur est habilité à une partie de l'application ou non
   */
  isCaregiver(): boolean {
    let role: string = this.getTokenRole();
    return !role || role === Role.ROLE_CAREGIVER;
  }

  /**
   * Id du token
   */
  getTokenId(): number {
    if (this.isAuthenticated() == false) return null;
    const token = this.getToken();
    const decoded = jwt_decode(token);
    return decoded.user_id == undefined ? null : parseInt(decoded.user_id);
  }

  /**
   * Permet de connaitre la date d'expiration du token utilisateur,
   * et ainsi le deconnecter de l'application
   */
  getTokenExpirationDate(): Date {
    if (this.isAuthenticated() == false) return null;
    const token = this.getToken();
    const decoded = jwt_decode(token);
    if (decoded.exp == undefined) return null;
    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  /**
   * Permet de savoir si le token a expiré ou non
   */
  isTokenExpired(): Boolean {
    if (this.getToken() == null) return true;
    const date = this.getTokenExpirationDate();
    if (date == undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
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
