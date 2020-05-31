import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-menu-add",
  templateUrl: "./menu-add.component.html",
  styleUrls: ["./menu-add.component.scss"],
})
export class MenuAddComponent implements OnInit {
  textures: string[] = ["Normal", "Mixé"];
  selectedButton: number = 0;
  selectedTexture: string;
  beginWeek: string;
  repeat: number = 5;

  error: string;

  constructor() {}

  ngOnInit(): void {}

  selectTexture(texture: string, index: number): void {
    this.selectedButton = index;
    this.selectedTexture = texture;
  }
}
