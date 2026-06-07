import { TipoMovimentacao } from "./EnumTipoMovimentacao";

export interface IMovimentacao {
  id: bigint;
  tipo: string;
  valor: number;
  data: Date;
  origem: string;
  destino: string;
}