export enum TipoUsuario {
  CLIENTE = 1,
  GERENTE = 2,
  ADMINISTRADOR = 3
}

export const TipoUsuarioLabel = {
  [TipoUsuario.CLIENTE]: 'Cliente',
  [TipoUsuario.GERENTE]: 'Gerente',
  [TipoUsuario.ADMINISTRADOR]: 'Administrador'
};