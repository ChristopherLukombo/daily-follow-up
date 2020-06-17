import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Menu } from "src/app/models/food/menu";

@Component({
  selector: "app-menu-declined-edit",
  templateUrl: "./menu-declined-edit.component.html",
  styleUrls: ["./menu-declined-edit.component.scss"],
})
export class MenuDeclinedEditComponent implements OnInit {
  menu: Menu;

  loading: boolean = false;
  warning: string;

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    if (!this.menu) {
      this.warning = "Impossible de charger le menu à modifier";
    }
    console.log(window.history.state.data);
    console.log(this.router.getCurrentNavigation().extras.state.data);
  }
}
