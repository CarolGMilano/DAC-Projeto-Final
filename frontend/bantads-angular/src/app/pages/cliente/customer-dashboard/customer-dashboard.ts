import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

type DashboardView = 'SALDO' | 'DEPOSITO' | 'SAQUE' | 'TRANSFERENCIA';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './customer-dashboard.html',
  styleUrl: './customer-dashboard.css',
})
export class CustomerDashboard {
    saldo: number = 67.00;
    limite: number = 200.00;
   view: DashboardView = 'SALDO';
   

  valorInput: number | null = null;
  destinatarioInput: string = '';

  mudarView(novaView: DashboardView) {
    this.view = novaView;
    this.valorInput = null;
    this.destinatarioInput = '';
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

