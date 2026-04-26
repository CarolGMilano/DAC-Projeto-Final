import { TipoUsuario } from "./EnumTipoUsuario";

export interface IGerente {
  id: number; 
  idUsuario: number;
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  senhaAtual?: string;
  novaSenha?: string;
  ativo: boolean;
  tipo: TipoUsuario;
}