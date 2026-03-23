import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from "../../../services";
import { ICliente } from '../../../shared';

type DashboardView = 'SALDO' | 'DEPOSITO' | 'SAQUE' | 'TRANSFERENCIA';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './customer-dashboard.html',
  styleUrl: './customer-dashboard.css',
})
export class CustomerDashboard {
  idCliente: number = 1;
  cliente!: ICliente;
  limite: number = 200.00;
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
    // buscando o cliente de ID 2 por padrao
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

