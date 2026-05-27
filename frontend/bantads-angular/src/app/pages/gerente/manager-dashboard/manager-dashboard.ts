import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IClienteAprovacaoResponse, SharedModule } from '../../../shared';
import { ClienteService } from '../../../services';

@Component({
  selector: 'app-manager-dashboard',
  imports: [SharedModule, CommonModule],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css',
})

export class ManagerDashboard {
  private clienteService = inject(ClienteService);

  clientes: IClienteAprovacaoResponse[] = [];

  ngOnInit(){
    this.listarPendentes();
  }

  listarPendentes() {
    this.clienteService.listarPendentes().subscribe({
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

  aprovar(cpf: String){
    console.log("aprovou" + cpf)
  }

  recusar(cpf: String){
    console.log("recusou" + cpf)
  }
}
