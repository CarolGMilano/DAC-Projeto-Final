import { TipoUsuario } from "./EnumTipoUsuario";

export interface Usuario {
  id: number;
  tipo: TipoUsuario;
  login: string;
  senha: string;
}