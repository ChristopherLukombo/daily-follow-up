import { Pipe, PipeTransform, Injectable } from "@angular/core";

@Pipe({
  name: "age",
})
@Injectable()
export class DetermineAgePipe implements PipeTransform {
  transform(birthday: string): number {
    if (!birthday) return;
    let diff = Math.abs(Date.now() - new Date(birthday).getTime());
    let age = Math.floor(diff / (1000 * 3600 * 24) / 365.25);
    return age;
  }
}
