import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'determineAge'
})
export class DetermineAgePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
