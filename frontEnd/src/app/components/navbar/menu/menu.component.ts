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
  /***/
  token: string = localStorage.getItem("token");

  constructor(
    private _eref: ElementRef,
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {}

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

  onLogout(): void {
    this.loginService.logout().subscribe();
    this.router.navigate(["/login"]);
  }
}
