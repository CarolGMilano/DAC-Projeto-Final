import { IMovimentacao } from "./IMovimentacao";

export interface IExtrato {
  movimentacoes: IMovimentacao[];
  conta: string;
  saldo: number;
}