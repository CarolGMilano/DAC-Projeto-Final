import { Component, inject } from '@angular/core';
import { IClienteCompletoResponse, IUsuarioLogado, TipoUsuario } from '../../shared';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService, GerenteService, LoginService } from '../../services';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  readonly loginService = inject(LoginService);
  readonly clienteService = inject(ClienteService);
  readonly gerenteService = inject(GerenteService);
  readonly router = inject(Router);

  usuario = {
    nome: '',
    email: ''
  };
  
  mensagemErro: string = '';

  usuarioLogado: IUsuarioLogado | null = null;
  TipoUsuario = TipoUsuario;

  ngOnInit() {
    this.usuarioLogado = this.loginService.usuarioLogado;
    this.buscar();
  }

  logout() {
    this.loginService.logout().subscribe({
      next: () => {
        this.usuarioLogado = null;
        this.router.navigate(['/']);
      },
      error: () => {
        this.usuarioLogado = null;
        this.router.navigate(['/']);
      }
    });
  }

  buscar() {
    const usuario = this.usuarioLogado;
    
    if (!usuario) return;

    const cpf = this.usuarioLogado?.usuario.cpf;

    if (!cpf) {
      this.mensagemErro = 'Usuário não autenticado.';

      return;
    }

    if(this.usuarioLogado?.tipo === TipoUsuario.CLIENTE){
      this.clienteService.buscar(cpf).subscribe({
        next: (cliente) => {
          this.usuario.nome = cliente.nome;
          this.usuario.email = cliente.email;
        },
        error: (erro) => {
          this.mensagemErro = erro.error?.message ?? 'Erro ao buscar cliente.';
        }
      });
    } else {
      this.gerenteService.buscar(cpf).subscribe({
        next: (gerente) => {
          this.usuario.nome = gerente.nome;
          this.usuario.email = gerente.email;
        },
        error: (erro) => {
          this.mensagemErro = erro.error?.message ?? 'Erro ao buscar cliente.';
        }
      });
    }

  }
}