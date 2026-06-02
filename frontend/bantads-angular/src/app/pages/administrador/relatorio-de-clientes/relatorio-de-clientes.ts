import { Component, inject } from '@angular/core';
import { ClienteService } from '../../../services';
import { ICliente, IClienteCompletoResponse, SharedModule } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-relatorio-de-clientes',
  imports: [MoedaBrPipe, SharedModule],
  templateUrl: './relatorio-de-clientes.html',
  styleUrl: './relatorio-de-clientes.css',
})
export class RelatorioDeClientes {
  private clienteService = inject(ClienteService);
  
  clientes?: IClienteCompletoResponse[];

  ngOnInit() {
    this.listarClientes();
  }

  listarClientes() {
    this.clienteService.listarDashboard().subscribe({
      next: (clientes) => {
        this.clientes = (clientes ?? []).sort((cliente1, cliente2) =>
          (cliente1.nome ?? '').localeCompare(cliente2.nome ?? '')
        );
      },
      error: (erro) => {
        if (erro.status === 500) {
          alert(`Erro interno: ${erro.error}`);
        } else {
          alert('Erro inesperado ao listar funcionários.');
          console.log(erro);
        }
      }
    });
  }
}
