import { IGerente } from "./IGerente";
import { IDadoConta } from "./IDadoConta";

export interface IDashboardAdminResponse {
  gerente: IGerente;
  clientes: IDadoConta[];
  saldo_positivo: number;
  saldo_negativo: number;
}