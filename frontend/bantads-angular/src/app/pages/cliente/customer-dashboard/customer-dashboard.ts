import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from "../../../services";
import { ICliente } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

type DashboardView = 'SALDO' | 'DEPOSITO' | 'SAQUE' | 'TRANSFERENCIA';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, MoedaBrPipe], 
  templateUrl: './customer-dashboard.html',
  styleUrl: './customer-dashboard.css',
})
export class CustomerDashboard implements OnInit { 
  idCliente: number = 1; 
  cliente!: ICliente;
  view: DashboardView = 'SALDO';

  valorInput: number | null = null;
  destinatarioInput: string = '';

  constructor(
    private clienteService: ClienteService,
    private router: Router 
  ) {}

  ngOnInit() {
    this.carregarDados();
  }

  carregarDados() {
    const dados = this.clienteService.getById(this.idCliente);
    if (dados) {
      this.cliente = { ...dados };
    }
  }

  mudarView(novaView: DashboardView) {
    this.view = novaView;
    this.valorInput = null;
    this.destinatarioInput = '';
    if (novaView === 'SALDO') this.carregarDados();
  }

  depositar() {
    if (this.valorInput && this.valorInput > 0) {
      this.cliente.saldo = (this.cliente.saldo || 0) + this.valorInput;
      this.salvarEAtualizar();
    }
  }

  saque() {
    const disponivel = (this.cliente.saldo || 0) + (this.cliente.limite || 0);
    if (this.valorInput && this.valorInput > 0 && this.valorInput <= disponivel) {
      this.cliente.saldo = (this.cliente.saldo || 0) - this.valorInput;
      this.salvarEAtualizar();
    } else {
      alert("Saldo e limite insuficientes!");
    }
  }

  transferir() {
    console.log('Transferência iniciada para:', this.destinatarioInput);

  }

  private salvarEAtualizar() {
    if (this.cliente && this.cliente.cpf) {
      this.clienteService.put(this.cliente.cpf, this.cliente);
      this.mudarView('SALDO');
    }
  }

  voltar() {
   
  }

  visualizarExtrato() {
    this.router.navigate(['/cliente/extrato']);
  }
}