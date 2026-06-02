export interface IClienteCompletoResponse {
  cpf: string;
  nome: string;
  telefone: string;
  email: string;

  cep: string | '';
  endereco: string;
  cidade: string;
  estado: string;
  salario: number;

  conta: string;
  saldo: number;
  limite: number;

  gerente: string;
  gerente_nome: string; 
  gerente_email: string;
}