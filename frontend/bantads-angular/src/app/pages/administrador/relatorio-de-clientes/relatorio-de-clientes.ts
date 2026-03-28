import { Component } from '@angular/core';
import { ClienteService } from '../../../services';
import { ICliente, SharedModule } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-relatorio-de-clientes',
  imports: [MoedaBrPipe, SharedModule],
  templateUrl: './relatorio-de-clientes.html',
  styleUrl: './relatorio-de-clientes.css',
})
export class RelatorioDeClientes {
  clientes!: ICliente[];

  constructor(
    private clienteService: ClienteService,
  
  ) {}

  ngOnInit() {
    this.clientes = this.clienteService.get().sort((a, b) =>
    a.nome.localeCompare(b.nome, 'pt-BR')
  );
  }
}
