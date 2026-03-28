/* R8: Consulta de extrato
O cliente pode, a qualquer momento, consultar seu extrato, informando a data de início e a data de fim. 
Devem ser apresentados: data/hora da transação, operação (transferência, depósito, saque), cliente origem/destino (preencher em caso de transferência), valor. 
Se o valor for de saída (transferência ou saque) o registro deve ser mostrado todo em vermelho. 
Se a operação for de entrada (transferência ou depósito) o registro deve ser mostrado todo em azul. 
A cada dia, desde a data inicial, deve-se mostrar o saldo consolidado naquele dia, mesmo que não tenha movimentações;
 */


/*
Anotações:
1. Imagino a parte da data de início/fim sendo puxado do vetor de transações, cujo dados serão puxados do back, que pega a data da transação mais recente como fim e da mais antiga do vetor como data de início.
*/

import { Component } from '@angular/core';

@Component({
  selector: 'app-bank-statement-lookup',
  imports: [],
  templateUrl: './bank-statement-lookup.html',
  styleUrl: './bank-statement-lookup.css',
})


export class BankStatementLookup {

    transacoesExemplo = [
    {id: 0, data: "05/03/2026", operacao: "Transferência", clienteOrigem: "JOÃO EUCLIDES DA CUNHA", clienteDestino: "MÁRIO QUINTANA", valor: "30,00 R$"},
    {id: 1, data: "08/03/2026", operacao: "Saque", clienteOrigem: "", clienteDestino: "", valor: "200,00 R$"},
    {id: 2, data: "09/03/2026", operacao: "Depósito", clienteOrigem: "", clienteDestino: "", valor: "500,00 R$"},
    {id: 3, data: "11/03/2026", operacao: "Transferência", clienteOrigem: "DALTON TREVISAN", clienteDestino: "JOÃO EUCLIDES DA CUNHA", valor: "1212,24 R$"}
  ]

}
