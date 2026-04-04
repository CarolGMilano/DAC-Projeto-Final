import { TipoUsuario } from "../EnumTipoUsuario";

export interface IUsuarioLogado {
  id: number;
  tipo: TipoUsuario;
  nome?: string; //Opcional, se vier no token. Podemos colocar outras informações também.
}