import { Component, OnInit, Input } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { Content } from "src/app/models/food/content";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Week } from "src/app/models/food/week";

@Component({
  selector: "app-menu-weeks-edit",
  templateUrl: "./menu-weeks-edit.component.html",
  styleUrls: ["./menu-weeks-edit.component.scss"],
})
export class MenuWeeksEditComponent implements OnInit {
  @Input() menu: Menu;

  allContents: Content[] = [];
  noContents: string;
  loading: boolean = false;

  moments: string[] = ["Déjeuner", "Dîner"];
  selectedWeek: number;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        if (data) {
          this.allContents = data;
        } else {
          this.noContents =
            "Il n'y a aucun plats disponibles actuellement dans la clinique. Veuillez d'abord en ajouter afin de pouvoir composer un menu.";
        }
        this.loading = false;
      },
      (error) => {
        this.noContents =
          "Une erreur s'est produite. Veuillez réessayer plus tard.";
        this.loading = false;
      }
    );
  }

  ngOnChanges(): void {
    if (this.menu) {
      console.log(this.menu);
      this.selectedWeek = this.menu.weeks[0].number;
    }
  }

  selectWeek(week: Week): void {
    this.selectedWeek = week.number;
  }
}
