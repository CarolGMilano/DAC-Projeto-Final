import { Component } from '@angular/core';
import { ClienteService } from '../../services';
import { ICliente } from '../../shared';

@Component({
  selector: 'app-relatorio-de-clientes',
  imports: [],
  templateUrl: './relatorio-de-clientes.html',
  styleUrl: './relatorio-de-clientes.css',
})
export class RelatorioDeClientes {
  clientes!: ICliente[];

  constructor(
    private clienteService: ClienteService,
  
  ) {}

  ngOnInit() {
    this.clientes = this.clienteService.get();
  }
}
