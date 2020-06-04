import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { Content } from "src/app/models/food/content";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { MomentDayCustomInfos } from "src/app/models/utils/moment-day-custom-infos";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { Action } from "src/app/models/utils/actions-enum";

@Component({
  selector: "app-menu-weeks",
  templateUrl: "./menu-weeks.component.html",
  styleUrls: ["./menu-weeks.component.scss"],
})
export class MenuWeeksComponent implements OnInit {
  moreLogo = faPlus;
  revertLogo = faMinus;

  weeks: number[] = [1, 2, 3, 4];
  selectedWeek: number = this.weeks[0];

  forms: FormGroup[] = [];
  days: string[] = [
    "Lundi",
    "Mardi",
    "Mercredi",
    "Jeudi",
    "Vendredi",
    "Samedi",
    "Dimanche",
  ];
  moments: string[] = ["Déjeuner", "Dîner"];

  allContents: Content[] = [];
  noContents: string;
  loading: boolean = false;

  @Output() submitMoment = new EventEmitter<MomentDayCustomInfos>();
  @Output() submitReplacement = new EventEmitter<ReplacementDTO>();
  @Output() addOrRemoveWeek = new EventEmitter<Action>();

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        if (data) {
          this.allContents = data;
        } else {
          this.noContents =
            "Il n'y a aucun plats disonibles actuellement dans la clinique. Veuillez d'abord en ajouter afin de pouvoir composer un menu.";
        }
      },
      (error) => {
        this.noContents =
          "Une erreur s'est produite. Veuillez réessayer plus tard.";
      },
      () => {
        this.loading = false;
      }
    );
  }

  createWeek(): void {
    this.weeks.push(this.weeks.length + 1);
    this.addOrRemoveWeek.emit(Action.ADD);
  }

  deleteWeek(): void {
    const index = this.weeks.indexOf(this.weeks.length);
    this.weeks.splice(index, 1);
    this.addOrRemoveWeek.emit(Action.REMOVE);
  }

  selectWeek(numWeek: number): void {
    this.selectedWeek = numWeek;
  }

  setMoment(infos: MomentDayCustomInfos): void {
    this.submitMoment.emit(infos);
  }

  setReplacement(dto: ReplacementDTO): void {
    this.submitReplacement.emit(dto);
  }
}
