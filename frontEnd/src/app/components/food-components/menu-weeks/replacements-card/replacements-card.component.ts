import { Component, OnInit, Input } from "@angular/core";
import { faEdit, faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { Content } from "src/app/models/food/content";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";

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

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.loadSuggestions();
    this.createForm();
  }

  createForm(): void {
    const target = {
      entries: [null, Validators.required],
      dishes: [null, Validators.required],
      starchies: [null, Validators.required],
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
    console.log(this.f);
    console.log("submit !");
  }
}
