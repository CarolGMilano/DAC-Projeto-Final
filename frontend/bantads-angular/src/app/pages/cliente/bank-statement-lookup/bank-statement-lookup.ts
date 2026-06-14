import { Component, inject, OnInit } from '@angular/core';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';
import { IClienteCompletoResponse, IExtrato, IMovimentacaoComSaldo } from '../../../shared';
import { ClienteService, ContaService, LoginService } from '../../../services';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Loading } from '../../../components';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-bank-statement-lookup',
  imports: [MoedaBrPipe, CommonModule, Loading, FormsModule],
  templateUrl: './bank-statement-lookup.html',
  styleUrl: './bank-statement-lookup.css',
})

export class BankStatementLookup implements OnInit {
  cliente!: IClienteCompletoResponse;
  extrato?: IExtrato;

  movimentacoesComSaldo: IMovimentacaoComSaldo[] = [];
  movimentacoesFiltradas: IMovimentacaoComSaldo[] = [];
  movimentacoesExibicao: IMovimentacaoComSaldo[] = [];

  dataInicio = this.getDataHoje();
  dataFim = this.getDataHoje();

  mensagemErro: string = '';

  private loginService = inject(LoginService);
  private contaService = inject(ContaService);
  private clienteService = inject(ClienteService);

  usuarioLogado = this.loginService.usuarioLogado;
  loading: boolean = false;

  hoje: Date = new Date();

  dataMinima: string = '';

  ngOnInit() {
    this.buscar();
  }

  obterDataPrimeiraMovimentacao(): string {
    if (!this.extrato?.movimentacoes.length) {
      return this.getDataHoje();
    }

    const primeiraMovimentacao =
      [...this.extrato.movimentacoes]
        .sort((a, b) =>
          new Date(a.data).getTime() -
          new Date(b.data).getTime()
        )[0];

    const data = new Date(primeiraMovimentacao.data);

    const ano = data.getFullYear();
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const dia = String(data.getDate()).padStart(2, '0');

    return `${ano}-${mes}-${dia}`;
  }

  getDataHoje(): string {
    const hoje = new Date();

    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, '0');
    const dia = String(hoje.getDate()).padStart(2, '0');

    return `${ano}-${mes}-${dia}`;
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
        console.log(cliente)
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

        this.dataMinima = this.obterDataPrimeiraMovimentacao();

        this.calcularSaldos();
        this.filtrarPorPeriodo();
      },
      error: err => {
        this.mensagemErro = err.error?.message ?? 'Erro ao realizar extrato.';
      }
    });
  }

  filtrarPorPeriodo() {
    this.movimentacoesExibicao = [];

    const [anoInicio, mesInicio, diaInicio] =
      this.dataInicio.split('-').map(Number);

    const [anoFim, mesFim, diaFim] =
      this.dataFim.split('-').map(Number);

    const inicio = new Date(anoInicio, mesInicio - 1, diaInicio);
    const fim = new Date(anoFim, mesFim - 1, diaFim);

    const dataMinima = new Date(this.dataMinima);

    if (inicio < dataMinima) {
      this.mensagemErro = 'A data inicial não pode ser anterior à primeira movimentação.';
      return;
    }

    if (fim > new Date()) {
      this.mensagemErro = 'A data final não pode ser futura.';
      return;
    }

    if (inicio > fim) {
      this.mensagemErro = 'A data inicial não pode ser maior que a data final.';
      return;
    }

    this.mensagemErro = '';

    const ultimaMovimentacaoAnterior =
      this.movimentacoesComSaldo
        .filter(mov => new Date(mov.data) < inicio)
        //Pega o último item da lista
        .at(-1);

    let saldoAnterior =
      ultimaMovimentacaoAnterior?.saldoCalculado ?? 0;

    for (
      let dia = new Date(inicio);
      dia <= fim;
      dia.setDate(dia.getDate() + 1)
    ) {

      const dataAtual =
        `${dia.getFullYear()}-${String(dia.getMonth() + 1).padStart(2, '0')}-${String(dia.getDate()).padStart(2, '0')}`;

      const movimentacoesDoDia = this.movimentacoesComSaldo.filter(mov => {
        const dataMov = new Date(mov.data);

        const dataMovStr =
          `${dataMov.getFullYear()}-${String(dataMov.getMonth() + 1).padStart(2, '0')}-${String(dataMov.getDate()).padStart(2, '0')}`;

        return dataMovStr === dataAtual;
      });

      if (movimentacoesDoDia.length > 0) {

        this.movimentacoesExibicao.push(...movimentacoesDoDia);

        saldoAnterior =
          movimentacoesDoDia[movimentacoesDoDia.length - 1].saldoCalculado;

      } else {

        this.movimentacoesExibicao.push({
          id: -1n,
          tipo: 'sem movimentação',
          valor: 0,
          data: new Date(
            dia.getFullYear(),
            dia.getMonth(),
            dia.getDate()
          ),
          origem: '',
          destino: '',
          saldoCalculado: saldoAnterior
        } as IMovimentacaoComSaldo);

      }
    }
  }

  calcularSaldos() {
    if (!this.extrato) return;

    this.movimentacoesComSaldo = [];

    const movimentacoesOrdenadas =
      [...this.extrato.movimentacoes]
        .sort((a, b) =>
          new Date(a.data).getTime() -
          new Date(b.data).getTime()
        );

    let saldo = 0;

    for (const mov of movimentacoesOrdenadas) {
      if (mov.tipo === 'depósito') {
        saldo += mov.valor;
      }

      else if (mov.tipo === 'saque') {
        saldo -= mov.valor;
      }

      else if (mov.tipo === 'transferência') {
        if (mov.destino === this.cliente.conta) {
          saldo += mov.valor;
        } else {
          saldo -= mov.valor;
        }
      }

      this.movimentacoesComSaldo.push({
        ...mov,
        saldoCalculado: saldo
      });
    }
  }
}