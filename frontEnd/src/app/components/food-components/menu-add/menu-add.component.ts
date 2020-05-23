import { Component, OnInit } from "@angular/core";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { Texture } from "src/app/models/food/texture";

@Component({
  selector: "app-menu-add",
  templateUrl: "./menu-add.component.html",
  styleUrls: ["./menu-add.component.scss"],
})
export class MenuAddComponent implements OnInit {
  textures: Texture[] = [];
  selectedButton: number = 0;
  selectedTexture: Texture;
  beginWeek: string;
  repeat: number = 5;

  loading: boolean = false;
  error: string;

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllTextures().subscribe(
      (data) => {
        this.textures = data.filter(
          (t) => t.name === "Normale" || t.name === "Mixé"
        );
        this.loading = false;
      },
      (error) => {
        this.error = this.getError(error);
        this.loading = false;
      }
    );
  }

  selectTexture(texture: Texture, index: number): void {
    this.selectedButton = index;
    this.selectedTexture = texture;
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getError(error: number): string {
    if (error && error === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur.";
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
