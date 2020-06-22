import { Injectable } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { Diet } from "src/app/models/patient/diet";
import { Content } from "src/app/models/food/content";
import { Replacement } from "src/app/models/food/replacement";
import { Week } from "src/app/models/food/week";

const QUANTITY_MAX_AUTHORIZED: number = 10;
const QUANTITY_MIN_AUTHORIZED: number = 20;

const ENTRIE_TYPE: string = "Entrée";
const DISH_TYPE: string = "Plat";
const GARNISH_TYPE: string = "Garniture";
const DAIRY_PRODUCT_TYPE: string = "P.L";
const DESSERT_TYPE: string = "Dessert";

@Injectable({
  providedIn: "root",
})
export class DeclinatorService {
  constructor() {}

  /**
   * Décline un menu en un autre menu conforme au régimes donnés
   * @param base
   * @param diets
   * @param allContents
   * @returns le nouveau Menu
   */
  declineMenuForDiets(base: Menu, diets: Diet[], allContents: Content[]): Menu {
    let declinedMenu: Menu = <Menu>JSON.parse(JSON.stringify(base));
    let dietsToRespect: Diet[] = Object.assign([], diets);
    let contents: Content[] = Object.assign([], allContents);
    let ingredientsToRespect: Map<
      string,
      number
    > = this.getAllIngredientsToRespect(dietsToRespect);

    // 1) carte de remplacement
    declinedMenu.replacement = this.generateDeclinedReplacement(
      ingredientsToRespect,
      contents
    );
    // 2) menus pour chaque jour
    declinedMenu.weeks = this.declineContentsForEachWeeks(
      declinedMenu.weeks,
      ingredientsToRespect,
      contents
    );
    // 3) régime du menu
    declinedMenu.diets = this.getDietsOfTheDeclinedMenu(dietsToRespect);

    return declinedMenu;
  }

  /**
   * Récupère tout les ingrédients à avoir en grande quantité ou moyenne quantité en fonction du régime
   * @param diets
   * @returns une Map de clés :nom d'ingrédients et de valeurs : 0/1 (= petite quantité/grande quantité)
   */
  getAllIngredientsToRespect(diets: Diet[]): Map<string, number> {
    let result: Map<string, number> = new Map<string, number>();
    diets.forEach((diet) => {
      diet.elementsToCheck.forEach(
        (hasToBeMoreOrLess: number, ingredient: string) => {
          result.set(ingredient, hasToBeMoreOrLess);
        }
      );
    });
    return result;
  }

  /**
   * Retourne la liste de diets du menu décliné
   * @param diets
   * @returns une liste de nom de diet
   */
  getDietsOfTheDeclinedMenu(diets: Diet[]): string[] {
    let dietsOfTheMenu: string[] = [];
    diets.forEach((diet) => dietsOfTheMenu.push(diet.name));
    return dietsOfTheMenu;
  }

  /**
   * Décline tout les plats de chaque semaine en fonction du régime donné
   * @param baseMenuWeeks
   * @param ingredientsToRespect
   * @param contents
   * @returns les semaines de plats déclinés
   */
  declineContentsForEachWeeks(
    baseMenuWeeks: Week[],
    ingredientsToRespect: Map<string, number>,
    contents: Content[]
  ): Week[] {
    baseMenuWeeks.forEach((week) =>
      week.days.forEach((day) =>
        day.momentDays.forEach((moment) => {
          moment.entry = this.replaceIfNotAuthorizedContent(
            moment.entry,
            ENTRIE_TYPE,
            ingredientsToRespect,
            contents
          );
          moment.dish = this.replaceIfNotAuthorizedContent(
            moment.dish,
            DISH_TYPE,
            ingredientsToRespect,
            contents
          );
          moment.garnish = this.replaceIfNotAuthorizedContent(
            moment.garnish,
            GARNISH_TYPE,
            ingredientsToRespect,
            contents
          );
          if (moment.name !== "Dîner") {
            moment.dairyProduct = this.replaceIfNotAuthorizedContent(
              moment.dairyProduct,
              DAIRY_PRODUCT_TYPE,
              ingredientsToRespect,
              contents
            );
          }
          moment.dessert = this.replaceIfNotAuthorizedContent(
            moment.dessert,
            DESSERT_TYPE,
            ingredientsToRespect,
            contents
          );
        })
      )
    );
    return baseMenuWeeks;
  }

  replaceIfNotAuthorizedContent(
    baseContent: Content,
    type: string,
    ingredientsToRespect: Map<string, number>,
    contents: Content[]
  ): Content {
    let authorizedContents: Content[] = this.getAuthorizedContentsByTypes(
      type,
      ingredientsToRespect,
      contents
    );
    let result: Content = baseContent;
    if (!authorizedContents.find((c) => c.name === baseContent.name)) {
      let alternativeContent = this.selectRandoms(
        authorizedContents
      ).find((newContent) => newContent.typeMeals.find((t) => t === type));
      result = alternativeContent;
    }
    return result;
  }

  /**
   * Remplace dans une liste de plat, les plats ne respectant pas
   * un régime donné
   * @param baseContents
   * @param ingredientsToRespect
   * @param contents
   * @returns une liste de plat conforme au régime
   */
  // A SUPPRIMER
  replaceNotAuthorizedContents(
    baseContents: Content[],
    ingredientsToRespect: Map<string, number>,
    contents: Content[]
  ): Content[] {
    let authorizedContents: Content[] = this.getAuthorizedContents(
      ingredientsToRespect,
      contents
    );
    let result: Content[] = [];
    baseContents.forEach((content) => {
      if (!authorizedContents.find((c) => c.name === content.name)) {
        let alternativeContent = authorizedContents.find((newContent) =>
          newContent.typeMeals.find((t) => t === content.typeMeals[0])
        );
        result.push(alternativeContent);
      } else {
        result.push(content);
      }
    });
    return result;
  }

  /**
   * Décline la carte de remplacement en fonction des plats disponibles dans la clinique
   * @param ingredientsToRespect
   * @param contents
   * @returns la nouvelle carte de remplacement
   */
  generateDeclinedReplacement(
    ingredientsToRespect: Map<string, number>,
    contents: Content[]
  ): Replacement {
    let declined: Replacement = new Replacement();
    declined.entries = this.selectRandoms(
      this.getAuthorizedContentsByTypes(
        ENTRIE_TYPE,
        ingredientsToRespect,
        contents
      )
    );
    declined.dishes = this.selectRandoms(
      this.getAuthorizedContentsByTypes(
        DISH_TYPE,
        ingredientsToRespect,
        contents
      )
    );
    declined.starchyFoods = this.selectRandoms(
      this.getAuthorizedContentsByTypes(
        GARNISH_TYPE,
        ingredientsToRespect,
        contents
      )
    );
    declined.vegetables = this.selectRandoms(
      this.getAuthorizedContentsByTypes(
        GARNISH_TYPE,
        ingredientsToRespect,
        contents
      )
    );
    declined.dairyProducts = this.selectRandoms(
      this.getAuthorizedContentsByTypes(
        DAIRY_PRODUCT_TYPE,
        ingredientsToRespect,
        contents
      )
    );
    declined.desserts = this.selectRandoms(
      this.getAuthorizedContentsByTypes(
        DESSERT_TYPE,
        ingredientsToRespect,
        contents
      )
    );
    return declined;
  }

  /**
   * Retourne tout les plats autorisé en fonction des caractéristiques d'un régime
   * et pour un type de plat (entrée, plat, dessert etc..)
   * @param type
   * @param ingredientsToRespect
   * @param contents
   * @returns une liste de Content
   */
  getAuthorizedContentsByTypes(
    type: string,
    ingredientsToRespect: Map<string, number>,
    contents: Content[]
  ): Content[] {
    return this.getAuthorizedContents(
      ingredientsToRespect,
      contents
    ).filter((content) => content.typeMeals.find((t) => t === type));
  }

  /**
   * Retourne tout les plats autorisés en fonction des caractéristiques d'un régime
   * @param ingredientsToRespect
   * @param contents
   * @returns une liste de Content
   */
  getAuthorizedContents(
    ingredientsToRespect: Map<string, number>,
    contents: Content[]
  ): Content[] {
    let authorizedContents: Content[] = contents;
    ingredientsToRespect.forEach(
      (hasToBeMoreOrLess: number, ingredient: string) => {
        if (hasToBeMoreOrLess === 0) {
          // l'ingredient doit etre inférieur à la limite autorisé
          authorizedContents = authorizedContents.filter(
            (content) => content[ingredient] < QUANTITY_MAX_AUTHORIZED
          );
        } else if (hasToBeMoreOrLess === 1) {
          // l'ingredient doit etre supérieur à la limite autorisé
          authorizedContents = authorizedContents.filter(
            (content) => content[ingredient] > QUANTITY_MIN_AUTHORIZED
          );
        }
      }
    );
    return authorizedContents;
  }

  /**
   * Sélectionne un nombre aléatoire de plats parmis d'une liste
   * de plat donnée
   * @param contents
   * @param number
   * @returns la liste de Content dont la taille max correspond
   * au nombre passé en paramètre (par defaut 5)
   */
  selectRandoms(contents: Content[], number: number = 5): Content[] {
    let tmp: Content[] = contents;
    let result: Content[] = [];
    tmp.sort(() => Math.random() - 0.5);
    for (let i = 0; i < number; i++) {
      if (tmp[i]) result[i] = tmp[i];
    }
    return result;
  }
}
