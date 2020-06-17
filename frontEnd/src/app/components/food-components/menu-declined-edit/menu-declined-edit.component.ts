import { Component, OnInit } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";

@Component({
  selector: "app-menu-declined-edit",
  templateUrl: "./menu-declined-edit.component.html",
  styleUrls: ["./menu-declined-edit.component.scss"],
})
export class MenuDeclinedEditComponent implements OnInit {
  menu: Menu;

  loading: boolean = false;
  warning: string;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getMenuFromLocal().subscribe(
      (data) => {
        this.menu = data;
        if (!this.menu) this.warning = this.impossibleToGetMenu();
        this.loading = false;
      },
      (error) => {
        this.warning = this.impossibleToGetMenu();
        this.loading = false;
      }
    );
  }

  impossibleToGetMenu(): string {
    return "Impossible de charger le menu à modifier";
  }
}
