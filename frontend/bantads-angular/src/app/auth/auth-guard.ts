import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

import { LoginService } from '../services';
import { TipoUsuario } from '../shared';

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  const usuarioLogado = loginService.usuarioLogado;
  const url = state.url;

  if (!usuarioLogado) {
    router.navigate([''], {
      queryParams: {
        error: `Deve fazer o login antes de acessar ${url}`
      }
    });

    return false;
  }

  // ROLE ERRADA
  if (route.data?.['role'] && !route.data['role'].includes(usuarioLogado.tipo)) {
    const home = redirecionarHome(usuarioLogado.tipo);

    router.navigate([home], {
      queryParams: {
        error: `Proibido o acesso a ${url}`
      }
    });

    return false;
  }

  return true;
};

function redirecionarHome(tipo: string): string {
  switch (tipo) {
    case TipoUsuario.CLIENTE:
      return '/customerDashboard';

    case TipoUsuario.GERENTE:
      return '/gerente';

    case TipoUsuario.ADMIN:
      return '/admin';

    default:
      return '/';
  }
}