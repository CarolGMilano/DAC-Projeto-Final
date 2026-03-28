import { Component } from '@angular/core';
import { SharedModule } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-manager-dashboard',
  imports: [SharedModule,MoedaBrPipe],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css',
})

/*
R9: Tela Inicial do Gerente - Deve apresentar todos os pedidos de 
autocadastro para aprovação, como uma tabela, contendo o CPF, Nome 
e salário do cliente, junto com dois botões "Aprovar" e "Recusar".
*/

export class ManagerDashboard {

exemplos: { id:number, cpf: string; nome: string; salario: number }[] = [
  { id: 1, cpf: "12345678909", nome: "Carlos Eduardo da Silva", salario: 3500 },
  { id: 2,cpf: "98765432100", nome: "Mariana Oliveira Santos", salario: 7200 },
  { id: 3,cpf: "45678912366", nome: "João Pedro Almeida Costa", salario: 2800 },
  { id: 4,cpf: "74185296311", nome: "Fernanda Souza Ribeiro", salario: 5100 },
  { id: 5,cpf: "15935725844", nome: "Lucas Henrique Martins", salario: 6400 },
  { id: 6,cpf: "85245615977", nome: "Patrícia Gomes Ferreira", salario: 4300 },
  { id: 7,cpf: "36925814722", nome: "Rafael Teixeira Barbosa", salario: 8900 }
];

recusar(id:number) {
 this.exemplos = this.exemplos.filter(c => c.id !== id);
}
aprovar(id: number) {
 this.exemplos = this.exemplos.filter(c => c.id !== id);
}

}
