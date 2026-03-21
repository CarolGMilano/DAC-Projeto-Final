import { Pipe, PipeTransform } from '@angular/core';

import { mascaraCEP } from '../../utils';

@Pipe({
  name: 'cepFormatador',
  standalone: false
})
export class CepFormatadorPipe implements PipeTransform {
  transform(value: string | undefined): string {
    if (value) {
      return mascaraCEP(value);
    } else {
      return '';
    } 
  }
}
