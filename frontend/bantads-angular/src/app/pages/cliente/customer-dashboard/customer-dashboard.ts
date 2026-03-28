import { Component, OnInit } from '@angular/core'; // Adicionei OnInit aqui
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
export class CustomerDashboard implements OnInit { // Adicione o 'implements OnInit'

  idCliente: number = 3; 
  cliente!: ICliente;
  view: DashboardView = 'SALDO';

  valorInput: number | null = null;
  destinatarioInput: string = '';

  constructor(private clienteService: ClienteService) {}

  mudarView(novaView: DashboardView) {
    this.view = novaView;
    this.valorInput = null;
    this.destinatarioInput = '';
    

    if (novaView === 'SALDO') {
      this.cliente = this.clienteService.getById(this.idCliente);
    }
  }

  ngOnInit() {
    // APAGUEI a linha do conflito que estava aqui
    this.cliente = this.clienteService.getById(this.idCliente);
  }

  // Métodos de Logística (Ainda apenas com console.log)
  depositar() {
      console.log('Depositando:', this.valorInput);
  }

  saque() {
    console.log('Sacando:', this.valorInput);
  }

  transferir() {
    console.log('Transferindo:', this.valorInput, 'Para:', this.destinatarioInput);
  }

  visualizarExtrato() {
    console.log('Extrato...');
  }
}