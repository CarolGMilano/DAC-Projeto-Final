import { Component, inject } from '@angular/core';
import { TipoUsuario } from '../../shared';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  usuarioLogado: TipoUsuario | null = null;
  TipoUsuario = TipoUsuario;

  router = inject(Router);

  ngOnInit() {
    const tipo = localStorage.getItem('tipoUsuario');
    this.usuarioLogado = tipo !== null ? Number(tipo) as TipoUsuario : null;
  }

}
