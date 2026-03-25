import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'primeiroNome'
})
export class PrimeiroNomePipe implements PipeTransform {

  transform(nomeCompleto: string | null | undefined): string {
    if (!nomeCompleto) return '';

    return nomeCompleto.trim().split(' ')[0];
  }

}
