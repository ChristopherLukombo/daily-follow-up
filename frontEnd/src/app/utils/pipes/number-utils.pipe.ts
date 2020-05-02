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

@Pipe({
  name: "gap",
})
@Injectable()
export class GapPipe implements PipeTransform {
  transform(first: number, second: number): number {
    if (first === undefined || !second === undefined) return;
    return Math.abs(first - second);
  }
}
