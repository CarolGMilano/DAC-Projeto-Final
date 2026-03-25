import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'moedaBr'
})
export class MoedaBrPipe implements PipeTransform {

  transform(valor: number | null | undefined): string {
    if (valor == null) return '';

    return valor.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    });
  }

}
