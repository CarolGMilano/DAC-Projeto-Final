import { Pipe, PipeTransform } from '@angular/core';

import { mascaraSalario } from '../../utils';

@Pipe({
  name: 'salarioFormatador',
  standalone: false
})
export class SalarioFormatadorPipe implements PipeTransform {
 
  transform(value: string | undefined): string {
    if (value) {
      return mascaraSalario(value);
    } else {
      return '';
    } 
  }
}