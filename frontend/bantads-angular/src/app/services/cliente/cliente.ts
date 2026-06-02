import { inject, Injectable } from '@angular/core';
import { IAutocadastro, IClienteAprovacaoResponse, IClienteListagemResponse, IClienteCompletoResponse, IMelhorClienteResponse, IClienteAtualizacao } from '../../shared';

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

  buscar(cpf: string, filtro?: string): Observable<IClienteCompletoResponse> {
    return this._httpClient.get<IClienteCompletoResponse>(
      `${this.BASE_URL}/${cpf}`,
      {
        params: filtro ? { filtro } : {}
      }
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  buscarPorIdUsuario(idUsuario: string): Observable<IClienteCompletoResponse> {
    return this._httpClient.get<IClienteCompletoResponse>(
      `${this.BASE_URL}/usuario/${idUsuario}`
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  listarClientesDoGerente(): Observable<IClienteListagemResponse[]> {
    return this._httpClient.get<IClienteListagemResponse[]>(
      this.BASE_URL,
      {
        observe: 'response'
      }
    ).pipe(
      map((resposta) => resposta.body ?? []),
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
  
  listarMelhoresClientes(): Observable<IMelhorClienteResponse[]> {
    return this._httpClient.get<IMelhorClienteResponse[]>(
      this.BASE_URL,
      {
        params: {
          filtro: 'melhores_clientes'
        },
        observe: 'response'
      }
    ).pipe(
      map((resposta) => resposta.body ?? []),
      catchError((erro) => throwError(() => erro))
    );
  }

  listarDashboard(): Observable<IClienteCompletoResponse[]> {
    return this._httpClient.get<IClienteCompletoResponse[]>(
      this.BASE_URL,
      {
        params: {
          filtro: 'adm_relatorio_clientes'
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

  atualizar(cpf: string, cliente: IClienteAtualizacao): Observable<IClienteAtualizacao> {
    console.log('BASE_URL:', this.BASE_URL);
    return this._httpClient.put<IClienteAtualizacao>(
      `${this.BASE_URL}/${cpf}`,
      cliente
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }
}