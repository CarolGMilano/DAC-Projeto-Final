import { inject, Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

import { ILogin, IUsuarioLogado } from '../../shared';

const LS_USUARIO_LOGADO = "usuarioLogado";
const LS_TOKEN = "token";

@Injectable({
  providedIn: 'root'
})

export class LoginService {
  private readonly _httpClient = inject(HttpClient);

  BASE_URL_LOGIN = "http://localhost:3000/login";
  BASE_URL_LOGOUT = "http://localhost:3000/logout";

  httpOptions = {
    observe: "response" as "response",
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  public get usuarioLogado(): IUsuarioLogado | null {
    let usuario = localStorage[LS_USUARIO_LOGADO];

    return (usuario ? JSON.parse(usuario) : null);
  }

  public set usuarioLogado(usuario: IUsuarioLogado) {
    localStorage[LS_USUARIO_LOGADO] = JSON.stringify(usuario);
  }

  login(login: ILogin): Observable<IUsuarioLogado> {
    return this._httpClient.post<IUsuarioLogado>(
      this.BASE_URL_LOGIN,
      login
    ).pipe(
      map(usuario => {
        console.log('LOGIN RESPONSE:', usuario);
        this.usuarioLogado = usuario;
        localStorage[LS_TOKEN] = usuario.access_token;

        return usuario;
      }),
      catchError((erro) => {
        return throwError(() => erro);
      })
    );
  }

  logout(): Observable<any> {
    return this._httpClient.post(
      this.BASE_URL_LOGOUT, {}
    ).pipe(
      map(() => {
        delete localStorage[LS_USUARIO_LOGADO];
        delete localStorage[LS_TOKEN];
      }),
      catchError((erro) => {
        return throwError(() => erro);
      })
    );
  }
}