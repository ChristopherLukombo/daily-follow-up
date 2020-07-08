import { Injectable } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { Day } from "src/app/models/food/day";
import * as moment from "moment";
import { MomentDay } from "src/app/models/food/moment-day";
import { Content } from "src/app/models/food/content";

@Injectable({
  providedIn: "root",
})
export class MenuUtilsService {
  constructor() {}

  /**
   * Retourne tout les plats disponnibles d'un menu le jour donné,
   * et du moment donné (dejeuner ou diner).
   * A cela vient s'ajouter les plats de la carte de remplacement
   * @param date
   * @param moment
   * @param menu
   * @returns la liste des plats
   */
  getAllContentsOfTheDate(
    date: string,
    moment: string,
    menus: Menu[]
  ): Content[] {
    // 1) la carte de remplacement
    let replacements: Content[] = [];
    let contentsOfTheMoment: Content[] = [];
    menus.forEach((menu) => {
      replacements = replacements.concat(
        this.getContentsOfReplacementCard(menu)
      );
      contentsOfTheMoment = contentsOfTheMoment.concat(
        this.getContentsOfMenuByMoment(date, moment, menu)
      );
    });
    return this.removeDuplicatesElements(
      replacements.concat(contentsOfTheMoment)
    );
  }

  /**
   * Retourne tout les plats d'une carte de remplacement
   * @param menu
   * @returns la liste des plats
   */
  getContentsOfReplacementCard(menu: Menu): Content[] {
    let card: Content[] = [];
    card = card
      .concat(menu.replacement.entries)
      .concat(menu.replacement.dishes)
      .concat(menu.replacement.garnishes)
      .concat(menu.replacement.dairyProducts)
      .concat(menu.replacement.desserts);
    return card;
  }

  /**
   * Retourne tout les plats d'un moment donnée (dejeuner ou diner) et d'une date donnée,
   * compris dans un menu donné
   * @param date
   * @param moment
   * @param menu
   * @returns la liste des plats
   */
  getContentsOfMenuByMoment(
    date: string,
    moment: string,
    menu: Menu
  ): Content[] {
    let contents: Content[] = [];
    let momentDay: MomentDay = this.getMomentOfMenuByDate(date, moment, menu);
    console.log(momentDay);
    contents.push(momentDay.entry);
    contents.push(momentDay.dish);
    contents.push(momentDay.garnish);
    if (moment !== "Dîner") contents.push(momentDay.dairyProduct);
    contents.push(momentDay.dessert);
    return contents;
  }

  /**
   * Retourne les plats d'un moment (dejeuner ou diner) d'une journée
   * @param date
   * @param moment
   * @param menu
   * @returns l'objet Moment, contenant les plats du dejeuner du jour, ou du diner du jour
   */
  getMomentOfMenuByDate(date: string, moment: string, menu: Menu): MomentDay {
    let day: Day = this.getDayOfMenuByDate(date, menu);
    if (!day || !day.momentDays) return;
    console.log(day);
    return day.momentDays.find((m) => m.name === moment);
  }

  /**
   * Retourne tout les plats (dejeuner + diner) d'une journée
   * en fonction du menu passé en parametre
   * @param date
   * @param menu
   * @returns l'objet Day, contenant les moments et les plats de chaque moment
   */
  getDayOfMenuByDate(date: string, menu: Menu): Day {
    let start: moment.Moment = moment(menu.startDate);
    let day: moment.Moment = moment(date);
    let indexDay: number = moment(day).diff(start, "days") + 1;
    return this.getDayOfTheMenu(indexDay, menu);
  }

  /**
   * Retourne tout les plats d'une journée d'un menu (plats du diner + plats du soir)
   * @param day
   * @param menu
   * @returns l'objet Day, contenant les moments et les plats de chaque moment
   */
  getDayOfTheMenu(day: number, menu: Menu): Day {
    let repetition: number = menu.repetition;
    let index: number = 1;
    for (let i = 0; i < repetition; i++) {
      for (let j = 0; j < menu.weeks.length; j++) {
        for (let k = 0; k < menu.weeks[j].days.length; k++) {
          if (index === day) return menu.weeks[j].days[k];
          index++;
        }
      }
    }
    return null;
  }

  /**
   * Retire les doublons
   * @param contents
   * @returns la liste des plats sans doublons
   */
  removeDuplicatesElements(contents: Content[]): Content[] {
    return Array.from(new Set(contents.map((c) => c.id))).map((id) => {
      return contents.find((content) => content.id === id);
    });
  }
}
