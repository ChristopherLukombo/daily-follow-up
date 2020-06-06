import { Component, OnInit, ElementRef } from "@angular/core";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import { Router } from "@angular/router";
import { LoginService } from "src/app/services/login/login.service";

@Component({
  selector: "app-menu",
  templateUrl: "./menu.component.html",
  styleUrls: ["./menu.component.scss"],
  host: {
    "(document:click)": "onClick($event)",
  },
})
export class MenuComponent implements OnInit {
  logoutLogo = faSignOutAlt;
  opened: boolean = false;

  constructor(
    private _eref: ElementRef,
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {}

  /**
   * Ouvre le menu detail
   */
  openDetails(): void {
    this.opened == false ? (this.opened = true) : (this.opened = false);
  }

  /**
   * Ferme la fenetre si l'on clique autre part (comme menu Google)
   * @param event
   */
  onClick(event): void {
    if (!this._eref.nativeElement.contains(event.target)) {
      this.opened = false;
    }
  }

  /**
   * Check si l'utilisateur est connecté à l'application
   */
  connected(): Boolean {
    return (
      this.loginService.isAuthenticated() &&
      !this.loginService.isTokenExpired() &&
      this.loginService.hasChangedPassword()
    );
  }

  /**
   * Retourne le pseudo
   */
  getPseudo(): string {
    return this.loginService.getTokenPseudo();
  }

  /**
   * Déconnexion à l'application
   */
  onLogout(): void {
    this.loginService.logout().subscribe();
    this.router.navigate(["/login"]);
  }
}
