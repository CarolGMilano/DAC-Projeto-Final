import { TipoMovimentacao } from "./EnumTipoMovimentacao";

export interface IMovimentacao {
  id: BigInteger;
  tipo: String;
  valor: number;
  data: Date;
  origem: string;
  destino: string;
}