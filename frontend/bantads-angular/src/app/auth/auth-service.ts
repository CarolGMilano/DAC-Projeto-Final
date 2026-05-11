  import { Injectable } from '@angular/core';
  import { BehaviorSubject, Observable, tap } from 'rxjs';
  import { HttpClient } from '@angular/common/http';
  import { Usuario } from '../shared/models/Usuario';
  import { TipoUsuario } from '../shared/models/EnumTipoUsuario';

  @Injectable({
    providedIn: 'root'
  })
  export class AuthService {

    private readonly STORAGE_KEY = 'token';
    private readonly USER_KEY = 'usuario';
    private usuarioSubject = new BehaviorSubject<Usuario | null>(null);
    usuario$ = this.usuarioSubject.asObservable();

    constructor(private http: HttpClient) {
      this.carregarDoLocalStorage();
    }

    login(login: string, senha: string): Observable<{ auth: boolean; token: string; data: Usuario }> {
      return this.http.post<{ auth: boolean; token: string; data: Usuario }>(
        'http://localhost:3000/login',
        { login, senha }
      ).pipe(
        tap(response => {
          if (response.auth) {
            localStorage.setItem(this.STORAGE_KEY, response.token);
            localStorage.setItem(this.USER_KEY, JSON.stringify(response.data));
            this.usuarioSubject.next(response.data);
          }
        })
      );
    }

    logout(): void {
      this.usuarioSubject.next(null);
      localStorage.removeItem(this.STORAGE_KEY);
      localStorage.removeItem(this.USER_KEY);
    }

    getUsuario(): Usuario | null {
      return this.usuarioSubject.value;
    }

    isAutenticado(): boolean {
      return !!localStorage.getItem(this.STORAGE_KEY);
    }

    temPermissao(roles: TipoUsuario[]): boolean {
      const usuario = this.usuarioSubject.value;
      if (!usuario) return false;
      return roles.includes(usuario.tipo);
    }

    private carregarDoLocalStorage(): void {
      const token = localStorage.getItem(this.STORAGE_KEY);
      const usuarioStr = localStorage.getItem(this.USER_KEY);

      if (token && usuarioStr) {
        const usuario: Usuario = JSON.parse(usuarioStr);
        this.usuarioSubject.next(usuario);
      }
    }
  }
