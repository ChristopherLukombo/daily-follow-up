import { Component, OnInit, Input } from "@angular/core";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import { Content } from "src/app/models/food/content";
import { Day } from "src/app/models/food/day";
import { CustomModal } from "src/app/models/utils/custom-modal";
import { Validators, FormGroup, FormBuilder } from "@angular/forms";
import { MomentDay } from "src/app/models/food/moment-day";
import { Menu } from "src/app/models/food/menu";

@Component({
  selector: "app-contents-day-menu-edit",
  templateUrl: "./contents-day-menu-edit.component.html",
  styleUrls: ["./contents-day-menu-edit.component.scss"],
})
export class ContentsDayMenuEditComponent implements OnInit {
  editLogo = faEdit;

  @Input() menu: Menu;
  @Input() week: number;
  @Input() day: Day;
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

  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    if (this.day && this.moment) {
      let moment: MomentDay = this.day.momentDays.find(
        (moment) => moment.name === this.moment
      );
      this.loadSuggestions();
      this.createFormAndRefreshContents(moment);
    }
  }

  ngOnChanges(): void {
    // Generation du modal unique pour chaque "moment-jour-semaine"
    this.modal = new CustomModal(
      this.moment + this.day.name + this.week,
      this.moment + this.day + this.week + "Label"
    );
  }

  createFormAndRefreshContents(moment: MomentDay): void {
    const target = {
      entry: [moment.contents[0], Validators.required],
      dish: [moment.contents[1], Validators.required],
      garnish: [moment.contents[2], Validators.required],
      dairyProduct: [moment.contents[3], Validators.required],
      dessert: [moment.contents[4], Validators.required],
    };
    this.form = this.formBuilder.group(target);
    this.refreshContents();
    if (this.moment === "Dîner") {
      this.form.removeControl("dairyProduct");
      this.suggestions.delete("P.L");
      this.contentsOfTheMoment.delete("P.L");
    }
  }

  get f() {
    return this.form.controls;
  }

  /**
   * On met à jour l'affichage des plats d'un moment - jour en fonction des valeurs du formulaire
   */
  refreshContents(): void {
    this.contentsOfTheMoment.set("Entrée", this.f.entry.value);
    this.contentsOfTheMoment.set("Plat", this.f.dish.value);
    this.contentsOfTheMoment.set("Garniture", this.f.garnish.value);
    if (this.moment !== "Dîner") {
      this.contentsOfTheMoment.set("P.L", this.f.dairyProduct.value);
    }
    this.contentsOfTheMoment.set("Dessert", this.f.dessert.value);
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

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) return;
    this.refreshContents();
    this.setContentsOfParentMenu();
    console.log("ok");
  }

  /**
   * Met à jour l'objet menu du composant parent
   */
  setContentsOfParentMenu(): void {
    let i: number = this.week - 1;
    let j: number = this.menu.weeks[i].days.findIndex(
      (d) => d.name === this.day.name
    );
    if (j !== -1) {
      let k = this.menu.weeks[i].days[j].momentDays.findIndex(
        (m) => m.name === this.moment
      );
      if (k !== -1) {
        this.menu.weeks[i].days[j].momentDays[k].contents = Array.from(
          this.contentsOfTheMoment.values()
        );
      }
    }
  }
}
