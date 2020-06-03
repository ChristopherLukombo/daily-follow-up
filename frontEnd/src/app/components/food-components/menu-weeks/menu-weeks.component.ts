import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { Content } from "src/app/models/food/content";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { MomentDayCustomInfos } from "src/app/models/utils/moment-day-custom-infos";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";

@Component({
  selector: "app-menu-weeks",
  templateUrl: "./menu-weeks.component.html",
  styleUrls: ["./menu-weeks.component.scss"],
})
export class MenuWeeksComponent implements OnInit {
  forms: FormGroup[] = [];
  weeks: number[] = [1, 2, 3, 4];
  selectedWeek: number = this.weeks[0];
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
  loading: boolean = false;

  @Output() submitMoment = new EventEmitter<MomentDayCustomInfos>();
  @Output() submitReplacement = new EventEmitter<ReplacementDTO>();

  constructor(private alimentationService: AlimentationService) {}

  ngOnInit(): void {
    this.loading = true;
    this.alimentationService.getAllContents().subscribe(
      (data) => {
        this.allContents = data;
      },
      (error) => {
        console.log(error);
      },
      () => {
        this.loading = false;
      }
    );
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
