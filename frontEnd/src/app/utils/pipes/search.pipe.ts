import { Pipe, PipeTransform, Injectable } from "@angular/core";

@Pipe({
  name: "search",
})
@Injectable()
export class SearchPipe implements PipeTransform {
  transform(items: any[], value: string, search: string): any[] {
    if (!search || !value) return items;

    return items.filter((item) => {
      const filter = item[value]
        .toString()
        .toLowerCase()
        .includes(search.toLowerCase());
      return filter;
    });
  }
}

@Pipe({
  name: "highlight",
})
@Injectable()
export class HighLightPipe implements PipeTransform {
  transform(value: any, args: string): any {
    if (!args) return value;

    return value
      .toString()
      .replace(
        new RegExp(args, "gi"),
        '<span class="highlight">' + args + "</span>"
      );
  }
}
