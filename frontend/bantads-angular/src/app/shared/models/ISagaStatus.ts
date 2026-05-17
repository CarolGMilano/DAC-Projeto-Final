export interface ISagaStatus {
  id: string;
  status: 'PROCESSANDO' | 'SUCESSO' | 'FALHA';
}