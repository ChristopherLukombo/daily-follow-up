import { Component, OnInit, Input } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import { Menu } from "src/app/models/food/menu";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Replacement } from "src/app/models/food/replacement";

@Component({
  selector: "app-replacements-card-edit",
  templateUrl: "./replacements-card-edit.component.html",
  styleUrls: ["./replacements-card-edit.component.scss"],
})
export class ReplacementsCardEditComponent implements OnInit {
  editLogo = faEdit;

  @Input() menu: Menu;
  @Input() allContents: Content[];

  form: FormGroup;

  suggestions: Map<string, Content[]> = new Map([
    ["Entrée", []],
    ["Plat", []],
    ["Garniture", []],
    ["P.L", []],
    ["Dessert", []],
  ]);

  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    if (this.menu) {
      this.loadSuggestions();
      this.createForm(this.menu.replacement);
    }
  }

  createForm(replacement: Replacement) {
    const target = {
      entries: [replacement.entries, Validators.required],
      dishes: [replacement.dishes, Validators.required],
      garnishes: [replacement.garnishes, Validators.required],
      dairyProducts: [replacement.dairyProducts, Validators.required],
      desserts: [replacement.desserts, Validators.required],
    };
    this.form = this.formBuilder.group(target);
  }

  loadSuggestions() {
    if (this.allContents) {
      this.suggestions.forEach((value: Content[], key: string) => {
        value = this.allContents.filter((c) =>
          c.typeMeals.find((t) => t === key)
        );
        this.suggestions.set(key, value);
      });
    }
  }

  get f() {
    return this.form.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.setReplacementsOfParentMenu();
  }

  /**
   * Met à jour l'objet menu du composant parent
   */
  setReplacementsOfParentMenu(): void {
    this.menu.replacement.entries = this.f.entries.value;
    this.menu.replacement.dishes = this.f.dishes.value;
    this.menu.replacement.garnishes = this.f.garnishes.value;
    this.menu.replacement.dairyProducts = this.f.dairyProducts.value;
    this.menu.replacement.desserts = this.f.desserts.value;
  }
}
