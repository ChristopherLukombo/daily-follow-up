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
import { TypeTexture } from "src/app/models/utils/texture-enum";
import { Router } from "@angular/router";
import { TypeMessage } from "src/app/models/utils/message-enum";

/**
 * @author neal
 * @version 17
 */
@Component({
  selector: "app-menu-add",
  templateUrl: "./menu-add.component.html",
  styleUrls: ["./menu-add.component.scss"],
})
export class MenuAddComponent implements OnInit {
  textures: string[] = [TypeTexture.NORMAL, TypeTexture.MIXED];
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
    private toastrService: ToastrService,
    private router: Router
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
          null,
          null,
          null,
          null,
          null
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
          if (this.invalidMoment(week.days[j].momentDays[k])) {
            return false;
          }
        }
      }
    }
    return true;
  }

  invalidMoment(moment: MomentDayDTO): boolean {
    return (
      !moment ||
      !moment.entry ||
      !moment.dish ||
      !moment.garnish ||
      (moment.name !== "Dîner" && !moment.dairyProduct) ||
      !moment.dessert
    );
  }

  repetitionsAreValids(): boolean {
    return (
      this.repeat &&
      Number.isInteger(this.repeat) &&
      this.repeat > 0 &&
      this.repeat < 7
    );
  }

  dateIsInTheFuture(week: number): boolean {
    let date = moment().day("Monday").week(week);
    let endOfCurrentWeek = moment().day("Sunday");
    return date > endOfCurrentWeek;
  }

  getMoment(day: string, week: number): string {
    return moment().day(day).week(week).format("YYYY-MM-DD");
  }

  getMenuDTO(beginWeek: number, repetition: number): MenuDTO {
    let begin = this.getMoment("Monday", beginWeek);
    let end = this.getMoment(
      "Sunday",
      beginWeek + this.weeksDTO.length * repetition
    );
    return new MenuDTO(
      null,
      begin,
      end,
      repetition,
      ["Normal"],
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
      !this.beginWeek ||
      !this.repetitionsAreValids() ||
      !this.replacementDTO ||
      !this.weeksAreValid()
    ) {
      this.error = TypeMessage.MENU_FORM_INVALID;
      return;
    }
    let week: number = parseInt(this.beginWeek.split("-")[1].replace("W", ""));
    if (!this.dateIsInTheFuture(week)) {
      this.error = TypeMessage.MENU_HAS_TO_BEGIN_AT_LEAST_NEXT_WEEK;
      return;
    }
    this.creating = true;
    let dto = this.getMenuDTO(week, this.repeat);
    this.alimentationService.createMenu(dto).subscribe(
      (data) => {
        this.toastrService.success(
          "Le menu a bien été créé",
          "Création terminée !"
        );
        this.creating = false;
        this.router.navigate(["/food/menu/currents"]);
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
      return TypeMessage.NOT_AUTHENTICATED;
    } else if (error && error.status === 500) {
      return error.error.message;
    } else {
      return TypeMessage.AN_ERROR_OCCURED;
    }
  }
}
