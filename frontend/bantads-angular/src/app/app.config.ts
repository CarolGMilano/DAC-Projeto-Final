import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './auth/auth-interceptor';

import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

import { LOCALE_ID } from '@angular/core';

registerLocaleData(localePt);

if (!sessionStorage.getItem('storageLimpo')) {
  localStorage.removeItem('usuarioLogado');
  localStorage.removeItem('token');

  sessionStorage.setItem('storageLimpo', 'true');
}

export const appConfig: ApplicationConfig = {
  providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([authInterceptor])
    ),
  ]
};
