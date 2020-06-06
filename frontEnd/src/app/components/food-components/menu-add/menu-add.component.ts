import { Component, OnInit } from "@angular/core";
import { MomentDayCustomInfos } from "src/app/models/utils/moment-day-custom-infos";
import { WeekDTO } from "src/app/models/dto/food/weekDTO";
import { DayDTO } from "src/app/models/dto/food/dayDTO";
import { MomentDayDTO } from "src/app/models/dto/food/moment-dayDTO";
import { ReplacementDTO } from "src/app/models/dto/food/replacementDTO";
import * as moment from "moment";
import { Action } from "src/app/models/utils/actions-enum";
import { MenuDTO } from "src/app/models/dto/food/menuDTO";
import { AlimentationService } from "src/app/services/alimentation/alimentation.service";
import { ToastrService } from "ngx-toastr";
import { HttpErrorResponse } from "@angular/common/http";

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
  replacementDTO: ReplacementDTO;

  creating: boolean = false;

  constructor(
    private alimentationService: AlimentationService,
    private toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.initWeeks();
  }

  /**
   * Initialisation de toute l'arborescence de la liste d'objet Week
   * (weeks > days > moments)
   */
  initWeeks(): void {
    for (let i = 0; i < this.weeksDTO.length; i++) {
      let week = this.initializeWeek(i + 1);
      this.weeksDTO[i] = week;
    }
  }

  initializeWeek(numWeek: number): WeekDTO {
    let week = new WeekDTO(null, numWeek, []);
    this.indexOfDays.forEach((indexDay: number, nameDay: string) => {
      week.days[indexDay] = new DayDTO(null, nameDay, []);
      this.indexOfMoments.forEach((indexMoment: number, nameMoment: string) => {
        week.days[indexDay].momentDays[indexMoment] = new MomentDayDTO(
          null,
          nameMoment,
          []
        );
      });
    });
    return week;
  }

  selectTexture(texture: string): void {
    this.selectedTexture = texture;
  }

  setReplacement(dto: ReplacementDTO): void {
    this.replacementDTO = dto;
  }

  setMoment(infos: MomentDayCustomInfos): void {
    this.setMenu(
      infos,
      infos.week - 1,
      this.indexOfDays.get(infos.day),
      this.indexOfMoments.get(infos.momentDayDTO.name)
    );
  }

  addOrRemoveWeek(action: Action): void {
    if (action === Action.ADD) {
      let week = this.initializeWeek(this.weeksDTO.length + 1);
      this.weeksDTO[this.weeksDTO.length] = week;
    } else if (action === Action.REMOVE) {
      this.weeksDTO.splice(-1, 1);
    }
  }

  setMenu(
    infos: MomentDayCustomInfos,
    indexWeek: number,
    indexDay: number,
    indexMoment: number
  ): void {
    // la semaine n'existe pas
    if (!this.weeksDTO[indexWeek]) {
      this.weeksDTO[indexWeek] = new WeekDTO(null, infos.week, []);
    }
    // si le jour n'existe pas
    if (!this.weeksDTO[indexWeek].days[indexDay]) {
      this.weeksDTO[indexWeek].days[indexDay] = new DayDTO(null, infos.day, []);
    }
    this.weeksDTO[indexWeek].days[indexDay].momentDays[indexMoment] =
      infos.momentDayDTO;
  }

  /**
   * Permet de savoir si tout les plats de chaque moment/jours de la semaines ont étés renseignés
   * @returns true si valide false si non
   */
  weeksAreValid(): boolean {
    for (let i = 0; i < this.weeksDTO.length; i++) {
      let week = this.weeksDTO[i];
      for (let j = 0; j < this.indexOfDays.size; j++) {
        if (!week.days[j]) return false;
        for (let k = 0; k < this.indexOfMoments.size; k++) {
          if (
            !week.days[j].momentDays[k] ||
            !week.days[j].momentDays[k].contents ||
            week.days[j].momentDays[k].contents.length === 0
          ) {
            return false;
          }
        }
      }
    }
    return true;
  }

  getMoment(day: string, week: number): string {
    return moment().day(day).week(week).format("YYYY-MM-DD");
  }

  getMenuDTO(begin: string, end: string): MenuDTO {
    return new MenuDTO(
      null,
      begin,
      end,
      "Normal",
      this.selectedTexture,
      this.replacementDTO,
      null,
      null,
      this.weeksDTO
    );
  }

  onCreate(): void {
    this.submitted = true;
    this.error = null;
    if (
      !this.weeksAreValid() ||
      !this.beginWeek ||
      !this.repeat ||
      !this.replacementDTO
    ) {
      this.error =
        "Le menu est incomplet. Veuillez vérifier la carte de remplacement ainsi que vos insertions pour chaque jour.";
      return;
    }
    this.creating = true;
    const indexWeek: number = parseInt(
      this.beginWeek.split("-")[1].replace("W", "")
    );
    let beginDate = this.getMoment("Monday", indexWeek);
    let endDate = this.getMoment(
      "Sunday",
      indexWeek + this.weeksDTO.length * this.repeat
    );
    // TODO : check la semaine déjà passé ou non
    let dto = this.getMenuDTO(beginDate, endDate);
    this.alimentationService.createMenu(dto).subscribe(
      (data) => {
        // TODO : redirection vers la page visualisation menu en cours
        this.toastrService.success(
          "Le menu a bien été crée",
          "Création terminée !"
        );
        this.creating = false;
      },
      (error) => {
        this.toastrService.error(this.getCustomError(error), "Oops !");
        this.creating = false;
      }
    );
  }

  /**
   * Récupération du code erreur et ajout du message à afficher
   * @param error
   * @returns le msg d'erreur
   */
  getCustomError(error: HttpErrorResponse): string {
    if (error && error.status === 401) {
      return "Vous n'êtes plus connecté, veuillez rafraichir le navigateur";
    } else if (error && error.status === 500) {
      return error.error.message;
    } else {
      return "Une erreur s'est produite. Veuillez réessayer plus tard.";
    }
  }
}
