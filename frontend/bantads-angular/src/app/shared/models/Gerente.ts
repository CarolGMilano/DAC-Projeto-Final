export interface Gerente {
  id: number; 
  nome: string;
  cpf: string;
  email: string;
  telefone: string;
  senhaAtual?: string;
  novaSenha?: string;
}