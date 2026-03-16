import { Component } from '@angular/core';

@Component({
  selector: 'app-manager-dashboard',
  imports: [],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css',
})

/*
R9: Tela Inicial do Gerente - Deve apresentar todos os pedidos de 
autocadastro para aprovação, como uma tabela, contendo o CPF, Nome 
e salário do cliente, junto com dois botões "Aprovar" e "Recusar".
*/

export class ManagerDashboard {

exemplos: { cpf: number; name: string; salario: number }[] = [
  { cpf: 1, name: 'John', salario: 10 },
  { cpf: 2, name: 'Sarah', salario: 20 },
  { cpf: 3, name: 'Sarah', salario: 20 }
];

recusarAutoCadastro(Cliente: any) {
}
aprovarAutoCadastro(Cliente: any) {
}

}
