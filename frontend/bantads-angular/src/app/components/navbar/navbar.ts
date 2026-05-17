import { Component, inject } from '@angular/core';
import { IUsuarioLogado, TipoUsuario } from '../../shared';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../services';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  readonly loginService = inject(LoginService);
  readonly router = inject(Router);

  usuarioLogado: IUsuarioLogado | null = null;
  TipoUsuario = TipoUsuario;

  ngOnInit() {
    this.usuarioLogado = this.loginService.usuarioLogado;
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
}