import { Component, inject } from '@angular/core';
import { ClienteService } from '../../../services';
import { ICliente, IClienteCompletoResponse, SharedModule } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';
import { Loading } from '../../../components';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-relatorio-de-clientes',
  imports: [MoedaBrPipe, SharedModule, Loading, CommonModule],
  templateUrl: './relatorio-de-clientes.html',
  styleUrl: './relatorio-de-clientes.css',
})
export class RelatorioDeClientes {
  private clienteService = inject(ClienteService);
  
  clientes?: IClienteCompletoResponse[];

  loading: boolean = false;

  ngOnInit() {
    this.listarClientes();
  }

  listarClientes() {
    this.loading = true;
    this.clienteService.listarDashboard().subscribe({
      next: (clientes) => {
        this.clientes = (clientes ?? []).sort((cliente1, cliente2) =>
          (cliente1.nome ?? '').localeCompare(cliente2.nome ?? '')
        );
        this.loading = false;
      },
      error: (erro) => {
        this.loading = false;
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
