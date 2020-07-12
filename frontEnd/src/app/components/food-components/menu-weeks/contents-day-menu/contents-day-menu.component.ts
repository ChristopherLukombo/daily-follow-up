import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Content } from "src/app/models/food/content";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { CustomModal } from "src/app/models/utils/custom-modal";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import { MomentDayDTO } from "src/app/models/dto/food/moment-dayDTO";
import { MomentDayCustomInfos } from "src/app/models/utils/moment-day-custom-infos";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-contents-day-menu",
  templateUrl: "./contents-day-menu.component.html",
  styleUrls: ["./contents-day-menu.component.scss"],
})
export class ContentsDayMenuComponent implements OnInit {
  editLogo = faEdit;

  @Input() week: number;
  @Input() day: string;
  @Input() moment: string;
  @Input() allContents: Content[];

  modal: CustomModal;
  form: FormGroup;

  suggestions: Map<string, Content[]> = new Map([
    ["Entrée", []],
    ["Plat", []],
    ["Garniture", []],
    ["P.L", []],
    ["Dessert", []],
  ]);

  // liste des plats qui sont affichés (!=pas le formulaire)
  contentsOfTheMoment: Map<string, Content> = new Map([
    ["Entrée", null],
    ["Plat", null],
    ["Garniture", null],
    ["P.L", null],
    ["Dessert", null],
  ]);

  @Output() submitMoment = new EventEmitter<MomentDayCustomInfos>();

  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.loadSuggestions();
    this.createForm();
  }

  ngOnChanges(): void {
    // Generation du modal unique pour chaque "moment-jour-semaine"
    this.modal = new CustomModal(
      this.moment + this.day + this.week,
      this.moment + this.day + this.week + "Label"
    );
  }

  createForm(): void {
    const target = {
      entry: [null, Validators.required],
      dish: [null, Validators.required],
      garnish: [null, Validators.required],
      dairyProduct: [null, Validators.required],
      dessert: [null, Validators.required],
    };
    this.form = this.formBuilder.group(target);
    if (this.moment === "Dîner") {
      this.form.removeControl("dairyProduct");
      this.suggestions.delete("P.L");
      this.contentsOfTheMoment.delete("P.L");
    }
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

  emptyContents(): boolean {
    for (let [key, value] of this.contentsOfTheMoment) {
      if (value === null || this.contentsOfTheMoment.get(key) === null) {
        return true;
      }
    }
    return false;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.contentsOfTheMoment.set("Entrée", this.f.entry.value);
    this.contentsOfTheMoment.set("Plat", this.f.dish.value);
    this.contentsOfTheMoment.set("Garniture", this.f.garnish.value);
    if (this.moment !== "Dîner") {
      this.contentsOfTheMoment.set("P.L", this.f.dairyProduct.value);
    }
    this.contentsOfTheMoment.set("Dessert", this.f.dessert.value);
    let infos: MomentDayCustomInfos = new MomentDayCustomInfos(
      this.week,
      this.day,
      new MomentDayDTO(
        null,
        this.moment,
        this.contentsOfTheMoment.get("Entrée"),
        this.contentsOfTheMoment.get("Plat"),
        this.contentsOfTheMoment.get("Garniture"),
        this.moment !== "Dîner" ? this.contentsOfTheMoment.get("P.L") : null,
        this.contentsOfTheMoment.get("Dessert")
      )
    );
    this.submitMoment.emit(infos);
  }
}
