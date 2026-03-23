import { IEndereco, TipoUsuario } from './index';

export interface ICliente {
  id?: number; //Opcional, pois o ID vem do banco
  tipo?: TipoUsuario;
  nome: string;
  cpf: string;
  telefone: string;
  email: string;
  estadoCivil: string; //Conferir com o professor o que "Estado" nos dados do cliente significa.
  salario: number | null;
  endereco: IEndereco; //Referência ao endereço correspondente
  saldo: number | null;
  limite: number;
  gerenteNome: string;
  gerenteCpf: string;
  numeroConta: string;
}