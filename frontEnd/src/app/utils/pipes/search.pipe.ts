import { Pipe, PipeTransform, Injectable } from "@angular/core";

@Pipe({
  name: "search",
})
@Injectable()
export class SearchPipe implements PipeTransform {
  transform(items: any[], field: string, search: string): any[] {
    if (!search || !field) return items;

    return items.filter((item) => {
      const filter = item[field]
        .toString()
        .toLowerCase()
        .includes(search.toLowerCase());
      return filter;
    });
  }
}

@Pipe({
  name: "searchInside",
})
@Injectable()
export class SearchInsidePipe implements PipeTransform {
  transform(items: any[], obj: any, field: string, search: string): any[] {
    if (!search || !field || !obj) return items;

    return items.filter((item) => {
      const filter = item[obj][field]
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

@Pipe({
  name: "orderBy",
})
@Injectable()
export class OrderPipe implements PipeTransform {
  transform(items: any[], field: string): any[] {
    if (!field || !items) return items;

    return items.sort((a: any, b: any) => a[field].localeCompare(b[field]));
  }
}

@Pipe({
  name: "orderByInside",
})
@Injectable()
export class OrderInsidePipe implements PipeTransform {
  transform(items: any[], obj: any, field: string): any[] {
    if (!field || !obj || !items) return items;

    return items.sort((a: any, b: any) =>
      a[obj][field].localeCompare(b[obj][field])
    );
  }
}
