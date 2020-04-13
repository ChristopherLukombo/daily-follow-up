import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "initialsOnly",
})
export class getInitialsPipe implements PipeTransform {
  transform(pseudo: string): string {
    if (!pseudo) return;
    const splitedPseudo = pseudo.toUpperCase().split("_");
    const lastNameInitial = splitedPseudo[0].charAt(0);
    const firstNameInitial = splitedPseudo[1].charAt(0);
    return firstNameInitial.concat(lastNameInitial);
  }
}
