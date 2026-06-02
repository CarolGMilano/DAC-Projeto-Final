import { Component, inject } from '@angular/core';
import { ClienteService } from '../../../services';
import { IMelhorClienteResponse, SharedModule } from '../../../shared';
import { PrimeiroNomePipe } from '../../../shared/pipes/primeiroNome/primeiro-nome-pipe';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-top3-clients',
  imports: [PrimeiroNomePipe, MoedaBrPipe, SharedModule],
  templateUrl: './top3-clients.html',
  styleUrl: './top3-clients.css',
})

export class Top3Clients {
  clientes: IMelhorClienteResponse[] = []; 

  private clienteService = inject(ClienteService);

  ngOnInit() {
    this.listarMelhores();
  }

  listarMelhores() {
    this.clienteService.listarMelhoresClientes().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
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
