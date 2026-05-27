import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { LoginService } from '../../../services';
import { ILogin, TipoUsuario, UsuarioStatus } from '../../../shared';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  @ViewChild('formLogin') formLogin! : NgForm;

  private router = inject(Router);
  private route = inject(ActivatedRoute);
  readonly loginService = inject(LoginService);
  
  login: ILogin = {
    login: '',
    senha: ''
  }
  
  usuarioStatus: UsuarioStatus = UsuarioStatus.Nenhum;
  status = UsuarioStatus;

  loading: boolean = false;
  mensagem!: string;

  ngOnInit(): void {
    const usuario = this.loginService.usuarioLogado;

    if(usuario) {
      const rotasPorTipo = {
        [TipoUsuario.CLIENTE]: '/cliente',
        [TipoUsuario.GERENTE]: '/gerente',
        [TipoUsuario.ADMIN]: '/admin'
      };

      const rota = rotasPorTipo[usuario.tipo];
    
      this.router.navigate([rota]);
    } else {
      this.route.queryParams.subscribe(params => {
        this.mensagem = params['error'];
      })
    }
  }

  aoMudarInput() {
    this.usuarioStatus = UsuarioStatus.Nenhum;
  }

  mostrarSenha: boolean = true;

  logar() {
    if (!this.formLogin.form.valid) return;

    this.loginService.login(this.login).subscribe({
      next: (usuario) => {
        if(usuario != null) {
          this.loginService.usuarioLogado = usuario;

          if (usuario.tipo === TipoUsuario.CLIENTE) {
            this.router.navigate(['/cliente']);
          } else if (usuario.tipo === TipoUsuario.GERENTE) {
            this.router.navigate(['/gerente']);
          } else if (usuario.tipo === TipoUsuario.ADMIN) {
            this.router.navigate(['/admin']);
          }
        }
      },
      error: (erro) => {

        if (erro.status === 401 || erro.status === 404) {
          this.mensagem = erro.error;
          this.usuarioStatus = UsuarioStatus.Invalido;
        } else if (erro.status === 500) {
          this.mensagem = 'Erro interno no servidor.';
          this.usuarioStatus = UsuarioStatus.Invalido;
        } else {
          this.mensagem = 'Erro inesperado ao efetuar login.';
          this.usuarioStatus = UsuarioStatus.Invalido;
        }
      }
    });
  }
}
