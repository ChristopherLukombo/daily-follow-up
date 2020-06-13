import { Injectable } from "@angular/core";
import { Menu } from "src/app/models/food/menu";
import { Diet } from "src/app/models/patient/diet";
import { Content } from "src/app/models/food/content";
import { Replacement } from "src/app/models/food/replacement";

@Injectable({
  providedIn: "root",
})
export class DeclinatorService {
  constructor() {}

  declineMenuForDiets(menu: Menu, diets: Diet[], contents: Content[]): Menu {
    let elementsToFilter: string[] = [];
    let declinedMenu: Menu = menu;
    diets.forEach((diet) => {
      diet.elementsToCheck.forEach((e) => elementsToFilter.push(e));
    });
    // 1) Carte de remplacement
    declinedMenu.replacement = this.getAuthorizedReplacements(
      contents,
      elementsToFilter
    );
    return declinedMenu;
  }

  /**
   * Retourne une carte de remplacement adapté en fonction des caractéristique du régime
   * @param contents liste de plat de la clinique
   * @param elementToCheck caractéristiques du régime à filtrer
   * @returns la nouvelle carte de remplacement
   */
  getAuthorizedReplacements(
    contents: Content[],
    keysToFilter: string[]
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
