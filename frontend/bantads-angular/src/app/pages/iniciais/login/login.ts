import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { LoginService } from '../../../services';
import { ILogin, TipoUsuario } from '../../../shared';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  @ViewChild('formLogin') formLogin! : NgForm;

  private router = inject(Router);
  readonly loginService = inject(LoginService);

  login: ILogin = {
    email: '',
    senha: ''
  }

  logar() {
    if (!this.formLogin.form.valid) return;

    const usuario = this.loginService.login(this.login.email, this.login.senha);

    if (usuario) {
      if (usuario.tipo === TipoUsuario.CLIENTE) {
        this.router.navigate(['/customerDashboard']);
      } else if (usuario.tipo === TipoUsuario.GERENTE) {
        this.router.navigate(['/gerente']);
      } else if (usuario.tipo === TipoUsuario.ADMINISTRADOR) {
        this.router.navigate(['/admin']);
      }

      console.log("Usuário logado");
    } else {
      console.log("Usuário não existe")
    }
  }
}
