import { Component } from '@angular/core';
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
export class CustomerDashboard {
  //var
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
  }

  ngOnInit() {
    this.cliente = this.clienteService.getById(this.idCliente);
  }


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

