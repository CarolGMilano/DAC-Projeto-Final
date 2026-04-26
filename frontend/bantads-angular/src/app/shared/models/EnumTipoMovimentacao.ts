export enum TipoMovimentacao {
  DEPOSITO = 1,
  SAQUE = 2,
  TRANSFERENCIA = 3
}

export const TipoMovimentacaoLabel = {
  [TipoMovimentacao.DEPOSITO]: 'Depósito',
  [TipoMovimentacao.SAQUE]: 'Saque',
  [TipoMovimentacao.TRANSFERENCIA]: 'Transferência'
};