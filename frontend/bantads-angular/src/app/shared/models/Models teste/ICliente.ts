import { TipoUsuario } from "./EnumTipoUsuario";
import { IEndereco } from "./IEndereco";

export interface ICliente {
  id?: number; //Opcional, pois o ID vem do banco
  tipo?: TipoUsuario;
  nome: string;
  cpf: string;
  telefone: string;
  salario: number;
  endereco: IEndereco; //Referência ao endereço correspondente
}