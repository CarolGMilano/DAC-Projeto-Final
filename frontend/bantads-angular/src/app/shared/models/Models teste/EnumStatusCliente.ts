export enum StatusCliente {
  ATIVO = 1,
  PENDENTE = 2,
  REJEITADO = 3
}

export const StatusClienteLabel = {
  [StatusCliente.ATIVO]: 'Ativo',
  [StatusCliente.PENDENTE]: 'Pendente',
  [StatusCliente.REJEITADO]: 'Rejeitado'
};