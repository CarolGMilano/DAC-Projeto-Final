import { TipoMovimentacao } from "./EnumTipoMovimentacao";

export interface IMovimentacao {
  dataHora: Date;
  tipo: TipoMovimentacao;
  clienteOrigem?: string;
  clienteDestino?: string;
  valor: number;
}