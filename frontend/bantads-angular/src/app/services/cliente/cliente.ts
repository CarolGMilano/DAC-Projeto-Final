import { inject, Injectable } from '@angular/core';
import { ICliente, IAutocadastro, IClienteAprovacaoResponse } from '../../shared';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private readonly _httpClient = inject(HttpClient);
  
  BASE_URL = "http://localhost:3000/clientes";
  
  httpOptions = {
    observe: "response" as "response",
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }
  
  inserir(cliente: IAutocadastro): Observable<IAutocadastro> {
    return this._httpClient.post<IAutocadastro>(
      this.BASE_URL,
      cliente
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  listarPendentes(): Observable<IClienteAprovacaoResponse[]> {
    return this._httpClient.get<IClienteAprovacaoResponse[]>(
      this.BASE_URL,
      {
        params: {
          filtro: 'para_aprovar'
        },
        observe: 'response'
      }
    ).pipe(
      map((resposta) => resposta.body ?? []),
      catchError((erro) => throwError(() => erro))
    );
  }

  aprovar(cpf: string): Observable<any> {
    return this._httpClient.post(
      `${this.BASE_URL}/${cpf}/aprovar`,
      {}
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  rejeitar(cpf: string, motivo: string): Observable<any> {
    return this._httpClient.post(
      `${this.BASE_URL}/${cpf}/rejeitar`,
      { motivo }
    );
  }
}