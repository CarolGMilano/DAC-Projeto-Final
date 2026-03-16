import { Component } from '@angular/core';

@Component({
  selector: 'app-customer-dashboard',
  imports: [],
  templateUrl: './customer-dashboard.html',
  styleUrl: './customer-dashboard.css',
})

export class CustomerDashboard {
  saldo: string = "5.00";

  // (mudar no futuro)
  // não existe um campo no html para inserir o valor, por enquanto o 
  // html está chamando as funções com o valor e destinatario = 1 por padrão

  depositar(valor: number) {
  }

  // mudar o  tipo do destinatario
  transferir(valor: number, destinatario: any) {
  }

  saque(valor: number) {
  }

  visualizarExtrato() {
  }

}

