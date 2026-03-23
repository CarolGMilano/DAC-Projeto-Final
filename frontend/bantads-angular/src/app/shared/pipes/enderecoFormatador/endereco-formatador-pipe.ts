import { Pipe, PipeTransform } from '@angular/core';
import { IEndereco } from '../../models';
import { mascaraCEP } from '../../utils';

@Pipe({
  name: 'enderecoFormatador',
  standalone: false
})
export class EnderecoFormatadorPipe implements PipeTransform {
  transform(endereco: IEndereco): string {
  if (endereco) {
    const logradouro = endereco.logradouro ?? '';
    const cidade = endereco.cidade ?? '';
    const estado = endereco.estado ?? '';
    const numero = endereco.numero ? `, ${endereco.numero}` : '';
    const complemento = endereco.complemento ? ` - ${endereco.complemento}` : '';
    const cepFormatado = endereco.cep ? ` - CEP: ${mascaraCEP(String(endereco.cep))}` : '';

    return `${logradouro}${numero}${complemento}, ${cidade}/${estado}${cepFormatado}`;
  } else {
    return '';
  }
}
}
