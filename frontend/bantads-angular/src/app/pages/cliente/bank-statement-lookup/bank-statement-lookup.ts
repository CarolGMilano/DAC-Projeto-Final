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

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bank-statement-lookup',
  imports: [],
  templateUrl: './bank-statement-lookup.html',
  styleUrl: './bank-statement-lookup.css',
})


export class BankStatementLookup implements OnInit{
  date = new Date("2026-02-01T00:00:00");
  date2 = new Date("2026-02-28T23:59:00");

    transacoesExemplo = [
    {id: 0, data: "02/02/2026", hora: "12:00", operacao: "Transferência", clienteOrigem: "JOÃO EUCLIDES DA CUNHA", clienteDestino: "MÁRIO QUINTANA", valor: "30,00 R$"},
    {id: 1, data: "08/02/2026", hora: "09:23", operacao: "Saque", clienteOrigem: "", clienteDestino: "", valor: "200,00 R$"},
    {id: 2, data: "09/02/2026", hora: "10:43", operacao: "Depósito", clienteOrigem: "", clienteDestino: "", valor: "500,00 R$"},
    {id: 3, data: "11/02/2026", hora: "01:20", operacao: "Transferência", clienteOrigem: "DALTON TREVISAN", clienteDestino: "JOÃO EUCLIDES DA CUNHA", valor: "1212,24 R$"},
    {id: 4, data: "14/02/2026", hora: "01:20", operacao: "Transferência", clienteOrigem: "DALTON TREVISAN", clienteDestino: "JOÃO EUCLIDES DA CUNHA", valor: "1212,24 R$"},
  ]
  // Todas as transações de tal período
  transactionsArray: any[] = [];

  ngOnInit(){
    this.transactionsArray = this.popularTransacoes(this.date, this.date2);
  }

  popularTransacoes(start: Date, end: Date): any[] {
    const result: any[] = [];
    const current = new Date(start);

    while (current <= end ) {
      const dataLocal = current.toLocaleDateString('pt-BR');
      // console.log(`dataLocal: ${dataLocal}, ${this.transacoesExemplo[0].data}`);
      

      // Filtra transações que pertencem a esse dia;
      const transacoesDoDia = this.transacoesExemplo.filter(t => t.data === dataLocal);

      const transacoesPush = transacoesDoDia.length > 0 ?
      transacoesDoDia :
      [{
        id: -1,
        data: dataLocal,
        hora: "",
        operacao: "",
        clienteOrigem: "",
        clienteDestino: "",
        valor: "0"
      }]
      const indexNew = result.push({
          data: dataLocal,
          transacoes: transacoesPush
      });

      // Consolog teste
      console.log(result[indexNew - 1]);

      // Próximo dia
      current.setDate(current.getDate() + 1);
    }
    return result;
  }

  // Consolog teste


}
