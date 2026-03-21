import { Injectable } from '@angular/core';
import { TipoUsuario } from '../../shared';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  //Service apenas para teste de rotas de acesso

  setTipoUsuario(tipo: TipoUsuario) {
    localStorage.setItem('tipoUsuario', tipo.toString());
  }

  login(email: string, senha: string) {
    if (email === 'cliente@teste.com') {
      this.setTipoUsuario(TipoUsuario.CLIENTE);
      
      return { tipo: TipoUsuario.CLIENTE };
    }

    if (email === 'gerente@teste.com') {
      this.setTipoUsuario(TipoUsuario.GERENTE);

      return { tipo: TipoUsuario.GERENTE };
    }

    if (email === 'admin@teste.com') {
      this.setTipoUsuario(TipoUsuario.ADMINISTRADOR);

      return { tipo: TipoUsuario.ADMINISTRADOR };
    }

    return null;
  }
}