import { Component, inject } from '@angular/core';
import { ClienteService } from '../../../services';
import { IMelhorClienteResponse, SharedModule } from '../../../shared';
import { PrimeiroNomePipe } from '../../../shared/pipes/primeiroNome/primeiro-nome-pipe';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';
import { Loading } from '../../../components';

@Component({
  selector: 'app-top3-clients',
  imports: [PrimeiroNomePipe, MoedaBrPipe, SharedModule, Loading],
  templateUrl: './top3-clients.html',
  styleUrl: './top3-clients.css',
})

export class Top3Clients {
  clientes: IMelhorClienteResponse[] = []; 

  private clienteService = inject(ClienteService);

  loading: boolean = false;

  ngOnInit() {
    this.listarMelhores();
  }

  listarMelhores() {
    this.loading = true;
    this.clienteService.listarMelhoresClientes().subscribe({
      next: (clientes) => {
        this.loading = false;
        this.clientes = clientes;
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
