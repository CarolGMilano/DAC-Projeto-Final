import { TipoUsuario } from "./EnumTipoUsuario";

export interface IGerente {
  id?: number; 
  idUsuario: number;
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  senha?: string;
  ativo: boolean;
  tipo: TipoUsuario;
}