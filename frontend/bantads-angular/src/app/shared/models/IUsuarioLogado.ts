import { TipoUsuario } from "./EnumTipoUsuario";

export interface IUsuarioLogado {
  access_token: string;
  token_type: string;
  tipo: TipoUsuario;
  usuario: {
    nome: string;
    cpf: string;
    email: string;
  };
}