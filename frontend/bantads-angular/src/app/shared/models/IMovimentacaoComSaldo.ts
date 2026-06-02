import { IMovimentacao } from "./IMovimentacao";

export interface IMovimentacaoComSaldo extends IMovimentacao {
  saldoCalculado: number;
}