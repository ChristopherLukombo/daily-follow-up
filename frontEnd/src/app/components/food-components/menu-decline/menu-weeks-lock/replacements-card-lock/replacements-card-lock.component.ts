import { Component, OnInit, Input } from "@angular/core";
import { Replacement } from "src/app/models/food/replacement";
import { Content } from "src/app/models/food/content";

@Component({
  selector: "app-replacements-card-lock",
  templateUrl: "./replacements-card-lock.component.html",
  styleUrls: ["./replacements-card-lock.component.scss"],
})
export class ReplacementsCardLockComponent implements OnInit {
  @Input() replacement: Replacement;

  card: Map<string, Content[]> = new Map<string, Content[]>();

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {
    if (this.replacement) this.loadCardReplacements();
  }

  loadCardReplacements(): void {
    this.card.set("Entrée(s)", this.replacement.entries);
    this.card.set("Plat(s)", this.replacement.dishes);
    this.card.set("Féculent(s)", this.replacement.starchyFoods);
    this.card.set("Légume(s)", this.replacement.vegetables);
    this.card.set("Produit(s) laitier(s)", this.replacement.dairyProducts);
    this.card.set("Dessert(s)", this.replacement.desserts);
  }

  /**
   * Obligatoire pour pas que la pipe
   * keyvalue casse l'ordre des clés
   */
  notOrdered = () => {
    return 0;
  };
}
