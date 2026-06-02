export enum TipoUsuario {
  CLIENTE = 'CLIENTE',
  GERENTE = 'GERENTE',
  ADMINISTRADOR = 'ADMINISTRADOR'
}

export const TipoUsuarioLabel = {
  [TipoUsuario.CLIENTE]: 'Cliente',
  [TipoUsuario.GERENTE]: 'Gerente',
  [TipoUsuario.ADMINISTRADOR]: 'Administrador'
};