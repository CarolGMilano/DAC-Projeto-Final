import { Component, inject, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IClienteAprovacaoResponse, SharedModule } from '../../../shared';
import { ClienteService } from '../../../services';
import { FormsModule, NgForm } from '@angular/forms';
import { Loading } from '../../../components';

@Component({
  selector: 'app-manager-dashboard',
  imports: [CommonModule, FormsModule, SharedModule, Loading],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css',
})

export class ManagerDashboard {
  @ViewChild('formRejeicao') formRejeicao! : NgForm;
  private clienteService = inject(ClienteService);

  clientes: IClienteAprovacaoResponse[] = [];
  motivoRejeicao: string = '';
  cpf: string = '';
  mostrarFormulario: boolean = false;

  loading: boolean = false;

  ngOnInit(){
    this.listarPendentes();
  }

  abrirPopup(cpf: string) {
    this.motivoRejeicao = '';
    this.cpf = cpf;

    this.mostrarFormulario = true;
  }

  cancelar() {
    this.motivoRejeicao = '';
    this.mostrarFormulario = false;
  }

  listarPendentes() {
    this.loading = true;
    this.clienteService.listarPendentes().subscribe({
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

  aprovar(cpf: string) {
    this.loading = true;
    this.clienteService.aprovar(cpf).subscribe({
      next: (res) => {
        console.log('Aprovado com sucesso!', res);
        this.listarPendentes();
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao aprovar:', err);
        this.loading = false;
      }
    });
  }

  recusar() {
    this.loading = true;
    if (!this.cpf || !this.motivoRejeicao) return;

    this.clienteService
      .rejeitar(this.cpf, this.motivoRejeicao)
      .subscribe({
        next: (res) => {
          this.listarPendentes();

          this.motivoRejeicao = '';
          this.mostrarFormulario = false;
          this.loading = false;
        },
        error: (err) => {
          console.error('Erro ao rejeitar:', err);
          this.loading = false;
        }
    });
  }
}
