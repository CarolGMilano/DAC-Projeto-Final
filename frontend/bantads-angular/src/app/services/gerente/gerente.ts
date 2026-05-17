import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';

import { IGerente, ISagaStatus } from '../../shared';

@Injectable({
  providedIn: 'root'
})

export class GerenteService {
  private readonly _httpClient = inject(HttpClient);
  
  BASE_URL = "http://localhost:3000/gerentes";

  httpOptions = {
    observe: "response" as "response",
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  listarTodos(): Observable<IGerente[] | null> {
    return this._httpClient.get<IGerente[]>(this.BASE_URL, {
      observe: 'response'
    }).pipe(
      map((resposta: HttpResponse<IGerente[]>) => {
        return resposta.body ?? [];
      }),
      catchError((erro) => throwError(() => erro))
    );
  }

  //Verifica o status da SAGA
  verificarStatus(id: number): Observable<ISagaStatus | null> {
    return this._httpClient.get<ISagaStatus>(
      `${this.BASE_URL}/status/${id}`,
      { observe: 'response' }
    ).pipe(
      map(res => res.body ?? null),
      catchError(err => throwError(() => err))
    );
  }

  buscar(cpf: string): Observable<IGerente> {
    return this._httpClient.get<IGerente>(
      `${this.BASE_URL}/${cpf}`
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  inserir(gerente: IGerente): Observable<IGerente> {
    return this._httpClient.post<IGerente>(
      this.BASE_URL,
      gerente
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  atualizar(gerente: IGerente): Observable<IGerente> {
    return this._httpClient.put<IGerente>(
      `${this.BASE_URL}/${gerente.cpf}`,
      gerente
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  remover(cpf: String): Observable<void> {
    return this._httpClient.delete<void>(
      `${this.BASE_URL}/${cpf}`
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }
}
