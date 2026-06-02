import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { IExtrato } from '../../shared';

@Injectable({
  providedIn: 'root',
})
export class ContaService {
    private readonly _httpClient = inject(HttpClient);
  
  BASE_URL = "http://localhost:3000/contas";
  
  httpOptions = {
    observe: "response" as "response",
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  depositar(conta: string, valor: number): Observable<any> {
    return this._httpClient.post(
      `${this.BASE_URL}/${conta}/depositar`,
      { valor }
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  sacar(conta: string, valor: number): Observable<any> {
    return this._httpClient.post(
      `${this.BASE_URL}/${conta}/sacar`,
      { valor }
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }
  
  transferir(conta: string, destino: string, valor: number): Observable<any> {
    return this._httpClient.post(
      `${this.BASE_URL}/${conta}/transferir`,
      { destino, valor }
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }

  mostrarExtrato(conta: string): Observable<IExtrato> {
    return this._httpClient.get<IExtrato>(
      `${this.BASE_URL}/${conta}/extrato`
    ).pipe(
      catchError((erro) => throwError(() => erro))
    );
  }
}