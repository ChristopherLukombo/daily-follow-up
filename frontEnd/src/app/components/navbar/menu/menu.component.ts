import { Component, OnInit, ElementRef } from "@angular/core";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-menu",
  templateUrl: "./menu.component.html",
  styleUrls: ["./menu.component.scss"],
  host: {
    "(document:click)": "onClick($event)"
  }
})
export class MenuComponent implements OnInit {
  logoutLogo = faSignOutAlt;
  opened: boolean = false;

  constructor(private _eref: ElementRef) {}

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
}
