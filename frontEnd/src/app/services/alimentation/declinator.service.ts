import { Injectable } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { Diet } from "src/app/models/patient/diet";
import { Content } from "src/app/models/food/content";
import { Replacement } from "src/app/models/food/replacement";

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

  declineMenuForDiets(
    base: Menu,
    dietsToRespect: Diet[],
    contents: Content[]
  ): Menu {
    let declinedMenu: Menu = Object.assign({}, base);
    let ingredientsToRespect: Map<
      string,
      number
    > = this.getAllIngredientsToRespect(dietsToRespect);
    console.log(ingredientsToRespect);

    // 1) Carte de remplacement
    declinedMenu.replacement = this.generateDeclinedReplacement(
      ingredientsToRespect,
      contents
    );
    console.log(declinedMenu.replacement);

    // 2) menus pour chaque jour

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

  /****************************************************************************************** */

  /**
   * Retourne une carte de remplacement adapté en fonction des caractéristique du régime
   * @param contents liste de plat de la clinique
   * @param elementToCheck caractéristiques du régime à filtrer
   * @returns la nouvelle carte de remplacement
   */
  getAuthorizedReplacementss(
    keysToFilter: string[],
    contents: Content[]
  ): Replacement {
    let newReplacement = new Replacement();
    Object.keys(newReplacement).forEach((propertyName) => {
      if (this.isArrayOfContents(newReplacement[propertyName])) {
        let authorizedContents: Content[] = contents;
        keysToFilter.forEach((key) => {
          authorizedContents = authorizedContents.filter((content) => {
            content[key] < 10;
          });
        });
        newReplacement[propertyName] = authorizedContents;
      }
    });
    return newReplacement;
  }

  /**
   * Vérifie si la propriété d'un objet est une liste de plat ou non
   * @param property
   * @returns true si oui, false si non
   */
  isArrayOfContents(property: any): boolean {
    return (
      Array.isArray(property) &&
      property.length &&
      property.every((item) => item instanceof Content)
    );
  }
}
