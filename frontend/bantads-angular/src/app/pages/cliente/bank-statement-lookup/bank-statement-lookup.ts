import { Component, inject, OnInit } from '@angular/core';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';
import { IClienteCompletoResponse, IExtrato, IMovimentacaoComSaldo } from '../../../shared';
import { ClienteService, ContaService, LoginService } from '../../../services';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Loading } from '../../../components';

@Component({
  selector: 'app-bank-statement-lookup',
  imports: [MoedaBrPipe, CommonModule, Loading],
  templateUrl: './bank-statement-lookup.html',
  styleUrl: './bank-statement-lookup.css',
})

export class BankStatementLookup implements OnInit {

  cliente!: IClienteCompletoResponse;
  extrato?: IExtrato;

  movimentacoesComSaldo: IMovimentacaoComSaldo[] = [];

  mensagemErro = '';

  private loginService = inject(LoginService);
  private contaService = inject(ContaService);
  private clienteService = inject(ClienteService);

  usuarioLogado = this.loginService.usuarioLogado;
  loading: boolean = false;

  hoje = new Date();

  ngOnInit() {
    this.buscar();
  }

  buscar() {
    const cpf = this.usuarioLogado?.usuario?.cpf;
    this.loading = true;

    if (!cpf) {
      this.mensagemErro = 'Usuário não autenticado.';
      this.loading = false;
      return;
    }

    this.clienteService.buscar(cpf).subscribe({
      next: cliente => {
        this.cliente = cliente;
        this.mostrarExtrato();
        this.loading = false;
      }, error: erro => {
        this.mensagemErro =  erro.error?.message ?? 'Erro ao buscar cliente.';
        this.loading = false;
      }
    });
  }

  mostrarExtrato() {
    if (!this.cliente?.conta) return;

    this.contaService.mostrarExtrato(this.cliente.conta).subscribe({
      next: extrato => {
        this.extrato = extrato;
        console.log(this.extrato)
        this.calcularSaldos();
      },
      error: err => {
        this.mensagemErro = err.error?.message ?? 'Erro ao realizar extrato.';
      }
    });
  }

  calcularSaldos() {
    if (!this.extrato) return;

    let saldoAtual = this.extrato.saldo;

    for ( let i = this.extrato.movimentacoes.length - 1; i >= 0; i--) {
      const mov = this.extrato.movimentacoes[i];

      this.movimentacoesComSaldo.unshift({
        ...mov,
        saldoCalculado: saldoAtual
      });

      const entrada =
        mov.tipo === 'depósito' ||
        (
          mov.tipo === 'transferência' &&
          mov.destino === this.usuarioLogado?.usuario?.cpf
        );

      saldoAtual += entrada
        ? -mov.valor
        : mov.valor;
    }
  }
}