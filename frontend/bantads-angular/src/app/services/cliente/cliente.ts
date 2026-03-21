import { Injectable } from '@angular/core';
import { ICliente } from '../../shared';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  salvar(cliente: ICliente) {
    console.log(cliente);
  }
}
