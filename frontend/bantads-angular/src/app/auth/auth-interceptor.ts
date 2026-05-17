import { HttpInterceptorFn } from '@angular/common/http';

//Como precisamos que todas as requisições tenham header pra autenticar o token, precisamos de um interceptor.
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  //Não precisa enviar header quando for login e autocadastro
  if (req.url.includes('/login') || req.url.includes('/cadastro')) {
    return next(req);
  }

  //Cria uma cópia da requisição e adiciona esse header nela, porque o Angular não deixa alterar a original.
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(authReq);
  }

  return next(req);
};
