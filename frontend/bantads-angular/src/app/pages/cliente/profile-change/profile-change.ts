import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from '../../../services';
import { ICliente } from '../../../shared';

@Component({
  selector: 'app-profile-change',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile-change.html',
  styleUrl: './profile-change.css',
})
export class ProfileChange implements OnInit {
  idCliente: number = 1; 
  cliente!: ICliente;

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const dados = this.clienteService.getById(this.idCliente);
    if (dados) {
      this.cliente = dados;
    }
  }

  atualizarPerfil(): void {
    if (this.cliente) {
      let novoLimiteCalculado = (this.cliente.salario || 0) * 0.5;
      const saldoAtual = this.cliente.saldo || 0;
      
      if (saldoAtual < 0) {
        const dividaAbsoluta = Math.abs(saldoAtual);

        if (novoLimiteCalculado < dividaAbsoluta) {
          novoLimiteCalculado = dividaAbsoluta;
        }
      }

  
      this.cliente.limite = novoLimiteCalculado;
      this.clienteService.put(this.cliente.cpf!, this.cliente);

      alert(`Perfil atualizado!\nNovo Limite: R$ ${this.cliente.limite.toFixed(2)}\nGerente: ${this.cliente.gerenteNome}`);
      
      this.voltar();
    }
  }

  voltar(): void {
    this.router.navigate(['/customerDashboard']);
  }
}