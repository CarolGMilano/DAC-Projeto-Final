import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Usuario } from '../shared/models/Usuario';
import { TipoUsuario } from '../shared/models/EnumTipoUsuario';


@Injectable
({
  providedIn: 'root'
})
export class AuthService
{

  private readonly STORAGE_KEY = 'usuario';

  private usuarioSubject = new BehaviorSubject<Usuario | null>(null);
  usuario$ = this.usuarioSubject.asObservable();

  constructor()
  {
    this.carregarDoLocalStorage();
  }

  setUsuario(usuario: Usuario): void
  {
    this.usuarioSubject.next(usuario);
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(usuario));
  }

  logout(): void
  {
    this.usuarioSubject.next(null);
    localStorage.removeItem(this.STORAGE_KEY);
  }

  getUsuario(): Usuario | null
  {
    return this.usuarioSubject.value;
  }

  isAutenticado(): boolean
  {
    return !!this.usuarioSubject.value;
  }

  temPermissao(roles: TipoUsuario[]): boolean
  {
    const usuario = this.usuarioSubject.value;
    if (!usuario) return false;
    return roles.includes(usuario.tipo);
  }

  private carregarDoLocalStorage(): void
  {
    const data = localStorage.getItem(this.STORAGE_KEY);
    if (data)
    {
      {
        const usuario: Usuario = JSON.parse(data);
        this.usuarioSubject.next(usuario);
      }
    }
  }
}