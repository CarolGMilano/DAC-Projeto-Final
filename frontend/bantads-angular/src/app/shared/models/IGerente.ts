import { TipoUsuario } from "./EnumTipoUsuario";

export interface IGerente {
  cpf: string;
  nome: string;
  email: string;
  tipo: string;
  //telefone: string;
  senha?: string;
}