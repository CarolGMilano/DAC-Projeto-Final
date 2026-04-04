import { ICliente } from "../ICliente";
import { IGerente } from "./IGerente";
import { IMovimentacao } from "./IMovimentacao";

export interface IConta {
  id?: number;
  cliente: ICliente;
  numeroConta: string;
  dataCriacao: Date;
  saldo: number;
  limite: number;
  gerente: IGerente;
  movimentacoes?: IMovimentacao[];
}