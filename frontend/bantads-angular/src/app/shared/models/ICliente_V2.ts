import { TipoUsuario } from "./EnumTipoUsuario";
import { IEndereco } from "./IEndereco";

export interface ICliente2 {
  id?: number; //Opcional, pois o ID vem do banco
  cpf: string;
  email: string;
  nome: string;
  telefone: string;
  salario: number | null;
  endereco: String;
  cep: string;
  cidade: string;
  estado: string;
}