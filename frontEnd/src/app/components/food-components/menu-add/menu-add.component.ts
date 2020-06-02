import { Component, OnInit } from "@angular/core";
import { MomentDayCustomInfos } from "src/app/models/utils/moment-day-custom-infos";
import { WeekDTO } from "src/app/models/dto/food/weekDTO";
import { DayDTO } from "src/app/models/dto/food/dayDTO";
import { MomentDayDTO } from "src/app/models/dto/food/moment-dayDTO";

@Component({
  selector: "app-menu-add",
  templateUrl: "./menu-add.component.html",
  styleUrls: ["./menu-add.component.scss"],
})
export class MenuAddComponent implements OnInit {
  textures: string[] = ["Normal", "Mixé"];
  selectedTexture: string = this.textures[0];
  beginWeek: string;
  repeat: number = 5;

  submitted: boolean = false;
  error: string;

  //Voir pour pas passer la liste de semaine dans les child pour pouvoir incrém/décrementer le nb de semaine
  weeksDTO: WeekDTO[] = new Array<WeekDTO>(4);
  indexOfDays: Map<string, number> = new Map([
    ["Lundi", 0],
    ["Mardi", 1],
    ["Mercredi", 2],
    ["Jeudi", 3],
    ["Vendredi", 4],
    ["Samedi", 5],
    ["Dimanche", 6],
  ]);
  indexOfMoments: Map<string, number> = new Map([
    ["Déjeuner", 0],
    ["Dîner", 1],
  ]);

  constructor() {}

  ngOnInit(): void {
    this.initWeeks();
  }

  /**
   * Initialisation de toute l'arborescence de la liste d'objet Week
   * (weeks > days > moments)
   */
  initWeeks(): void {
    for (let i = 0; i < this.weeksDTO.length; i++) {
      let week = new WeekDTO(null, i + 1, []);
      this.indexOfDays.forEach((indexDay: number, nameDay: string) => {
        week.days[indexDay] = new DayDTO(null, nameDay, []);
        this.indexOfMoments.forEach(
          (indexMoment: number, nameMoment: string) => {
            week.days[indexDay].momentsDays[indexMoment] = new MomentDayDTO(
              null,
              nameMoment,
              []
            );
          }
        );
      });
      this.weeksDTO[i] = week;
    }
  }

  selectTexture(texture: string): void {
    this.selectedTexture = texture;
  }

  setMoment(infos: MomentDayCustomInfos): void {
    this.setMenu(
      infos,
      infos.week - 1,
      this.indexOfDays.get(infos.day),
      this.indexOfMoments.get(infos.momentDayDTO.name)
    );
    console.log(this.weeksDTO);
    console.log(this.weeksAreValid());
  }

  setMenu(
    infos: MomentDayCustomInfos,
    indexWeek: number,
    indexDay: number,
    indexMoment: number
  ): void {
    // la semaine n'existe pas
    if (!this.weeksDTO[indexWeek]) {
      console.log("error");
      this.weeksDTO[indexWeek] = new WeekDTO(null, infos.week, []);
    }
    // si le jour n'existe pas
    if (!this.weeksDTO[indexWeek].days[indexDay]) {
      console.log("error error");
      this.weeksDTO[indexWeek].days[indexDay] = new DayDTO(null, infos.day, []);
    }
    this.weeksDTO[indexWeek].days[indexDay].momentsDays[indexMoment] =
      infos.momentDayDTO;
  }

  onCreate(): void {
    this.submitted = true;
    this.error = null;
    if (!this.weeksAreValid() || !this.beginWeek) {
      this.error =
        "Le menu est incomplet, veuillez vérifier vos insertions pour chaque jour";
      return;
    }
    console.log("creation du menu...");
  }

  weeksAreValid(): boolean {
    for (let i = 0; i < this.weeksDTO.length; i++) {
      let week = this.weeksDTO[i];
      for (let j = 0; j < this.indexOfDays.size; j++) {
        if (!week.days[j]) return false;
        for (let k = 0; k < this.indexOfMoments.size; k++) {
          if (
            !week.days[j].momentsDays[k] ||
            !week.days[j].momentsDays[k].contents ||
            week.days[j].momentsDays[k].contents.length === 0
          ) {
            return false;
          }
        }
      }
    }
    return true;
  }
}
