import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../auth/auth-service';
import { TipoUsuario } from '../shared/models/EnumTipoUsuario';

@Injectable
({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate
{

  constructor
  (
    private router: Router,
    private authService: AuthService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean
  {

    if (!this.authService.isAutenticado())
    {
      this.redirecionarParaLogin(state.url);
      return false;
    }

    const rolesPermitidas: TipoUsuario[] = route.data['role'] || [];

    if (rolesPermitidas.length > 0 && !this.authService.temPermissao(rolesPermitidas))
    {
      this.redirecionarParaLogin();
      return false;
    }

    return true;
  }

  private redirecionarParaLogin(urlDestino?: string): void
  {
    this.router.navigate(['/login'],{queryParams: { redirectTo: urlDestino }});
  }
}