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
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-bank-statement-lookup',
  imports: [MoedaBrPipe],
  templateUrl: './bank-statement-lookup.html',
  styleUrl: './bank-statement-lookup.css',
})


export class BankStatementLookup implements OnInit{

  // Período estabelecido para mostrar as transações;
  date = new Date("2026-02-01T00:00:00");
  date2 = new Date("2026-02-28T23:59:00");

  // Saldo Inicial fictício
  saldoInicial: number = 3289;

  // Transações de exemplo;
    transacoesExemplo = [
    {id: 0, data: "02/02/2026", hora: "12:00", operacao: "Transferência", clienteOrigem: "JOÃO EUCLIDES DA CUNHA", clienteDestino: "MÁRIO QUINTANA", valor: -30, saldoNoMomento: 0},
    {id: 1, data: "08/02/2026", hora: "09:23", operacao: "Saque", clienteOrigem: "", clienteDestino: "", valor: -200, saldoNoMomento: 0},
    {id: 2, data: "09/02/2026", hora: "10:43", operacao: "Depósito", clienteOrigem: "", clienteDestino: "", valor: 500, saldoNoMomento: 0},
    {id: 3, data: "11/02/2026", hora: "01:20", operacao: "Transferência", clienteOrigem: "DALTON TREVISAN", clienteDestino: "JOÃO EUCLIDES DA CUNHA", valor: 1212, saldoNoMomento: 0},
    {id: 4, data: "14/02/2026", hora: "01:20", operacao: "Transferência", clienteOrigem: "DALTON TREVISAN", clienteDestino: "JOÃO EUCLIDES DA CUNHA", valor: 500, saldoNoMomento: 0},
    {id: 5, data: "14/02/2026", hora: "02:20", operacao: "Transferência", clienteOrigem: "DALTON TREVISAN", clienteDestino: "JOÃO EUCLIDES DA CUNHA", valor: 200, saldoNoMomento: 0},
    {id: 6, data: "21/02/2026", hora: "15:16", operacao: "Saque", clienteOrigem: "", clienteDestino: "", valor: -800, saldoNoMomento: 0},
  ]
  // TRANSACTIONS ARRAY: Vetor contendo TODAS as transações de tal período, independente de página
  transactionsArray: any[] = [];

  // Variáveis relacionadas a página
    // Página atual
    pagAtual: number = 0;
    // Máx transações p/pag
    tamPag: number = 15;
  
  // Inicializa o TRANSACTIONS ARRAY para ter todas as transações de tal periodo;
  ngOnInit(){
    this.transactionsArray = this.popularTransacoes(this.date, this.date2, this.saldoInicial);
  }

  // 1.FUNÇÕES
    // 1.1 POPULAR TRANSAÇÕES: Itera por cada dia, se uma do transacoesExemplo bater o dia, ele é colocado dentro;
    
    /* O formato da população funciona da seguinte forma:
        // O vetor result é um vetor de objetos (87), contendo a data e um outro vetor de objetos, que é a transacoesDoDia:
          // transacoesDoDia pega as transacoes que batem com o dia com filter() e coloca no vetor result, em seu respectivo dia;
    */

  popularTransacoes(start: Date, end: Date, balance: number): any[] {
    const result: any[] = []; // Vai ser o transactionsArray quando obter todas as transações do período
    const current = new Date(start); // Acompanha a data durante a iteração do while;

    while (current <= end ) { // Para cada dia, obtem todas as transações no dia e coloca 
      const dataLocal = current.toLocaleDateString('pt-BR');
      // console.log(`dataLocal: ${dataLocal}, ${this.transacoesExemplo[0].data}`);
      

      // Filtra transações que pertencem a esse dia;
      const transacoesDoDia = this.transacoesExemplo.filter(t => t.data === dataLocal);

      // Coloca o saldo do dia
      transacoesDoDia.forEach((e) => {
        this.saldoInicial += e.valor
        e.saldoNoMomento = this.saldoInicial;
        console.log(`saldoInicial após mudança no valor: ${this.saldoInicial}`);
        console.log(`saldoNoMomento após receber saldoInicial: ${e.saldoNoMomento}`);
      }
      );

      // Se tiver transações, pega as transações filtradas
        // Caso contrário, cria uma transação vazia
      const transacoesPush = transacoesDoDia.length > 0 ?
      transacoesDoDia :
      [{
        id: -1,
        data: dataLocal,
        hora: "",
        operacao: "",
        clienteOrigem: "",
        clienteDestino: "",
        valor: "0",
        saldoNoMomento: this.saldoInicial
      }]

      // Coloca no vetor result a data e as transações
      const indexNew = result.push({
          data: dataLocal,
          transacoes: transacoesPush
      });

      // Consolog teste
      // console.log(result[indexNew - 1]);

      // Move para o próximo dia
      current.setDate(current.getDate() + 1);
    }
    return result;
  }

  // Paginação
    get paginatedTransactions(): any[] {
    const start = this.pagAtual * this.tamPag;
    const end = start + this.tamPag;
    return this.transactionsArray.slice(start, end);
  }
  
    // Próxima página
  proximaPagina() {
    if((this.pagAtual + 1) * this.tamPag < this.transactionsArray.length) {
      this.pagAtual++;
    } else {
      console.log("proximaPagina: deu ruim");      
    }
  }
    // Página anterior
  paginaAnterior(){
    if(this.pagAtual > 0) {
      this.pagAtual--;
    }
  }
  


}
