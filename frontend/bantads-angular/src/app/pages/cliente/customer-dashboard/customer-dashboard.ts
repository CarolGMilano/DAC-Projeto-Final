import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService, ContaService, LoginService } from "../../../services";
import { ICliente, IClienteCompletoResponse, IExtrato, IUsuarioLogado, SharedModule } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';
import { Loading } from '../../../components/loading/loading';

type DashboardView = 'SALDO' | 'DEPOSITO' | 'SAQUE' | 'TRANSFERENCIA';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, MoedaBrPipe, SharedModule, Loading], 
  templateUrl: './customer-dashboard.html',
  styleUrl: './customer-dashboard.css',
})
export class CustomerDashboard implements OnInit { 
  cliente!: IClienteCompletoResponse;
  view: DashboardView = 'SALDO';
  contaDestino: string = '';

  private loginService = inject(LoginService);
  private contaService = inject(ContaService);
  private clienteService = inject(ClienteService);
  
  private router = inject(Router);
  
  usuarioLogado = this.loginService.usuarioLogado;
  
  mensagemErro: string = '';

  valorFormatado: string = '';
  loading: boolean = false;

  ngOnInit() {
    this.buscar();
  }

  get limiteUtilizado(): number {
    return this.cliente.saldo < 0
      ? Math.abs(this.cliente.saldo)
      : 0;
  }

  get limiteDisponivel(): number {
    return this.cliente.saldo < 0
      ? this.cliente.limite - Math.abs(this.cliente.saldo)
      : this.cliente.limite;
  }

  get valorValido(): boolean {
    return this.valorParaNumero(this.valorFormatado) > 0;
  }

  get transferenciaValida(): boolean {
    return (
      this.valorParaNumero(this.valorFormatado) > 0 &&
      /^\d{4}$/.test(this.contaDestino)
    );
  }

  valorParaNumero(valor: string): number {
    if (!valor) return 0;

    return Number(valor.replace(/\D/g, '')) / 100;
  }

  buscar() {
    const usuario = this.usuarioLogado;
    
    if (!usuario) return;
    this.loading = true;

    const cpf = this.usuarioLogado?.usuario.cpf;

    if (!cpf) {
      this.mensagemErro = 'Usuário não autenticado.';
      this.loading = false;

      return;
    }

    this.clienteService.buscar(cpf).subscribe({
      next: (cliente) => {
        this.cliente = cliente;
        this.loading = false;
      },
      error: (erro) => {
        this.mensagemErro = erro.error?.message ?? 'Erro ao buscar cliente.';
        this.loading = false;
      }
    });
  }

  mudarView(novaView: DashboardView) {
    this.view = novaView;
    this.valorFormatado = '';
    this.contaDestino = '';
  }

  depositar() {
    if (!this.valorFormatado || !this.cliente?.conta) return;
    this.loading = true;

    const valor = this.valorParaNumero(this.valorFormatado);

    if (valor <= 0) {
      this.mensagemErro = 'Informe um valor válido.';
      this.loading = false;

      return;
    }

    this.contaService.depositar(this.cliente.conta, valor).subscribe({
      next: () => {
        this.valorFormatado = '';
        this.buscar();
        this.loading = false;
      },
      error: (err) => {
        this.mensagemErro = err.error?.message ?? 'Erro ao realizar depósito.';
        this.loading = false;

        console.error('Erro ao depositar:', err);
      }
    });
  }

  sacar() {
    if (!this.valorFormatado || !this.cliente?.conta) return;
    this.loading = true;

    const valor = this.valorParaNumero(this.valorFormatado);

    if (valor <= 0) {
      this.mensagemErro = 'Informe um valor válido.';
      this.loading = false;

      return;
    }

    this.contaService.sacar(this.cliente.conta, valor).subscribe({
      next: () => {
        this.valorFormatado = '';
        this.buscar();
        this.loading = false;
      },
      error: (err) => {
        this.mensagemErro = err.error?.message ?? 'Erro ao realizar saque.';
        this.loading = false;

        console.error('Erro ao depositar:', err);
      }
    });
  }

  transferir() {
    if (!this.valorFormatado || !this.cliente?.conta || !this.contaDestino) return;
    this.loading = true;

    const valor = this.valorParaNumero(this.valorFormatado);

    if (valor <= 0) {
      this.mensagemErro = 'Informe um valor válido.';
      this.loading = false;

      return;
    }

    this.contaService.transferir(this.cliente.conta, this.contaDestino, valor).subscribe({
      next: () => {
        this.valorFormatado = '';
        this.buscar();
        this.loading = false;
      },
      error: (err) => {
        this.mensagemErro = err.error?.message ?? 'Erro ao realizar saque.';
        this.loading = false;
        console.error('Erro ao depositar:', err);
      }
    });
  }
}