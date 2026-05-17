export enum TipoUsuario {
  CLIENTE = 'CLIENTE',
  GERENTE = 'GERENTE',
  ADMIN = 'ADMIN'
}

export const TipoUsuarioLabel = {
  [TipoUsuario.CLIENTE]: 'Cliente',
  [TipoUsuario.GERENTE]: 'Gerente',
  [TipoUsuario.ADMIN]: 'Administrador'
};