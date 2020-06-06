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

@Pipe({
  name: "actionPatient",
})
export class getActionPatientPipe implements PipeTransform {
  transform(action: string): string {
    if (!action) return;
    if (action === "INSERTED") return "Arrivée du patient";
    if (action === "UPDATED") return "Modification du patient";
    if (action === "DELETED") return "Le patient a quitté la clinique";
    if (action === "RECREATED") return "Le patient est revenu dans la clinique";
  }
}

@Pipe({
  name: "truncate",
})
export class truncateStringPipe implements PipeTransform {
  transform(value: string, limit: number) {
    if (!value || !limit) return;
    return value.length > limit ? value.substr(0, limit) + "..." : value;
  }
}

@Pipe({
  name: "floorName",
})
export class getFloorNamePipe implements PipeTransform {
  transform(number: number): string {
    if (number === undefined || number === null || number < 0) return;
    if (number === 0) return "RDC";
    if (number === 1) {
      return "1er étage";
    } else {
      return number + "ème étage";
    }
  }
}
