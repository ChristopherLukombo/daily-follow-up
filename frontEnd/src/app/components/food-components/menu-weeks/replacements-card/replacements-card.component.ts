import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { faEdit, faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { Content } from "src/app/models/food/content";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";

@Component({
  selector: "app-replacements-card",
  templateUrl: "./replacements-card.component.html",
  styleUrls: ["./replacements-card.component.scss"],
})
export class ReplacementsCardComponent implements OnInit {
  editLogo = faEdit;
  addLogo = faPlus;
  removeLogo = faMinus;

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

  @Output() submitReplacement = new EventEmitter<ReplacementDTO>();

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.loadSuggestions();
    this.createForm();
  }

  createForm(): void {
    const target = {
      entries: [null, Validators.required],
      dishes: [null, Validators.required],
      starchyFoods: [null, Validators.required],
      vegetables: [null, Validators.required],
      dairyProducts: [null, Validators.required],
      desserts: [null, Validators.required],
    };
    this.form = this.formBuilder.group(target);
  }

  loadSuggestions(): void {
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
    const dto = new ReplacementDTO(
      null,
      this.f.entries.value,
      this.f.dishes.value,
      this.f.starchyFoods.value,
      this.f.vegetables.value,
      this.f.dairyProducts.value,
      this.f.desserts.value
    );
    this.submitReplacement.emit(dto);
  }
}
