export interface IEndereco {
  id?: number; //Opcional
  cep: string;
  logradouro: string;
  numero?: number;
  complemento?: string;
  cidade: string;
  estado: string;
}