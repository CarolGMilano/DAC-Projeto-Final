import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService, LoginService } from '../../../services';
import { ICliente, IClienteCompletoResponse, SharedModule, IClienteAtualizacao, IMensagemErro } from '../../../shared';
import { Loading } from '../../../components';

@Component({
  selector: 'app-profile-change',
  standalone: true,
  imports: [CommonModule, FormsModule, SharedModule, Loading],
  templateUrl: './profile-change.html',
  styleUrl: './profile-change.css',
})
export class ProfileChange implements OnInit {
  private clienteService = inject(ClienteService);
  private loginService = inject(LoginService);
  private router = inject(Router);
  
  cliente!: IClienteCompletoResponse;
  clienteAtualizado!: IClienteAtualizacao;
  usuarioLogado = this.loginService.usuarioLogado;
  
  mensagemErro?: IMensagemErro;

  valorFormatado: string = '';
  loading: boolean = false;

  ngOnInit(): void {
    this.buscar();
  }

  get formularioValido(): boolean {
    const emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(
      this.clienteAtualizado?.email ?? ''
    );

    return !!(
      this.clienteAtualizado?.nome &&
      emailValido &&
      this.clienteAtualizado?.CEP &&
      this.clienteAtualizado?.endereco &&
      this.clienteAtualizado?.cidade &&
      this.clienteAtualizado?.estado &&
      this.valorParaNumero(this.valorFormatado) > 0
    );
  }

  get salarioValido(): boolean {
    const valor = this.valorParaNumero(this.valorFormatado);
    return valor > 0;
  }

  valorParaNumero(valor: string): number {
    if (!valor) return 0;

    return Number(valor.replace(/\D/g, '')) / 100;
  }

  valorParaString(valor: number): string {
    return valor.toLocaleString('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
  }

  buscar() {
    const usuario = this.usuarioLogado;

    if (!usuario) return;

    this.loading = true;

    const cpf = this.usuarioLogado?.usuario.cpf;

    if (!cpf) {
      this.mensagemErro = {
        tipo: 'autencicação',
        message: 'Usuário não autenticado.'
      };

      this.loading = false;
      return;
    }

    this.clienteService.buscar(cpf, "para_alterar").subscribe({
      next: (cliente) => {
        console.log(cliente)
        this.cliente = cliente;
        this.valorFormatado = this.valorParaString(cliente.salario);
        this.loading = false;
        this.cliente = cliente;

        this.clienteAtualizado = {
          nome: cliente.nome,
          email: cliente.email,
          salario: cliente.salario,
          CEP: cliente.cep,
          endereco: cliente.endereco,
          cidade: cliente.cidade,
          estado: cliente.estado
        };
        console.log(this.clienteAtualizado)
      },
      error: (erro) => {
        this.mensagemErro = erro.error ?? 'Erro ao buscar cliente.';

        this.loading = false;
      }
    });
  }

  atualizar() {
    if (!this.clienteAtualizado) return;

    this.loading = true;

    const emailAlterado = this.cliente.email !== this.clienteAtualizado.email;

    const clienteAtualizado = {
      ...this.clienteAtualizado,
      salario: this.valorParaNumero(this.valorFormatado)
    };

    this.clienteService.atualizar(this.cliente.cpf, clienteAtualizado).subscribe({
      next: () => {
        this.loading = false;

        if (emailAlterado) {
          localStorage.removeItem('token');
          this.router.navigate(['/login']);
        }
      },

      error: (erro) => {
        this.mensagemErro = erro.error;
        
        this.loading = false;
      }
    });
  }
}