import { TipoUsuario } from "./EnumTipoUsuario";
import { IEndereco } from "./IEndereco";

export interface ICliente2 {
  id?: number; //Opcional, pois o ID vem do banco
  tipo?: TipoUsuario;
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  salario: number | null;
  endereco: IEndereco; //Referência ao endereço correspondente
}