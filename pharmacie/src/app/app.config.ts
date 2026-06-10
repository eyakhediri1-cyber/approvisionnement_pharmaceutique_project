import { ApplicationConfig } from '@angular/core';
import { provideRouter, withRouterConfig } from '@angular/router';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideClientHydration } from '@angular/platform-browser';

import { routes } from './app.routes';
import { jwtInterceptor } from './interceptors/jwt.interceptor';

/**
 * CORRECTION :
 * - Ajout de withInterceptors([jwtInterceptor]) pour injecter le JWT automatiquement
 * - Suppression de provideClientHydration() qui cause des conflits SSR avec le localStorage
 */
export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes, withRouterConfig({ onSameUrlNavigation: 'reload' })),
    provideClientHydration(),
    provideHttpClient(
      withFetch(),
      withInterceptors([jwtInterceptor])  // ← NOUVEAU : intercepteur JWT
    )
  ]
};