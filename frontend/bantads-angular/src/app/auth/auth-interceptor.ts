import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

//Como precisamos que todas as requisições tenham header pra autenticar o token, precisamos de um interceptor.
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  const token = localStorage.getItem('token');

  //Não precisa enviar header quando for login e autocadastro
  const rotasSemHeader = req.url.includes('/login') || req.url.includes('/cadastro');

  let authReq = req;

  //Cria uma cópia da requisição e adiciona esse header nela, porque o Angular não deixa alterar a original.
  if (!rotasSemHeader && token) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(authReq).pipe(
    catchError((error) => {
      if (error.status === 401) {
        localStorage.removeItem('token');
        localStorage.removeItem('usuarioLogado');
        router.navigate(['/login']);
      }

      return throwError(() => error);
    })
  );
};
