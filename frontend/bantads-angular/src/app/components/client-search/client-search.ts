import { Component } from '@angular/core';

@Component({
  selector: 'app-client-search',
  imports: [],
  templateUrl: './client-search.html',
  styleUrl: './client-search.css',
})
//R12: Consultar Todos os Clientes - Deve apresentar em uma tabela todos os seus clientes, contendo CPF, Nome, Cidade, Estado, Saldo da conta, Limite da conta. Deve ser ordenado de forma crescente por Nome. Deve ser disponibilizado um campo de texto onde o gerente pode pesquisar o cliente por CPF (ou parte dele) e Nome (ou parte dele). Cada cliente deve possuir um link que, ao ser pressionado, vai para uma tela contendo todos os dados do cliente e de sua conta;
//R13: Consultar Cliente - Em uma tela em branco, o gerente deve informar em um campo de texto o CPF, o sistema deve mostrar todos os dados do cliente, incluindo os dados de sua conta (saldo e limite);

export class ClientSearch {

}
