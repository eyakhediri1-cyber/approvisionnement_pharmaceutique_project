import { HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

/**
 * Intercepteur HTTP JWT — injecte automatiquement le token Bearer
 * dans le header Authorization de chaque requête vers le backend.
 *
 * CORRECTION : remplace l'ancienne approche session PHP (pas de token)
 * par l'authentification JWT Spring Boot.
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);

  // Ne pas injecter le token côté serveur (SSR)
  if (!isPlatformBrowser(platformId)) {
    return next(req);
  }

  const token = localStorage.getItem('authToken');

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(cloned);
  }

  return next(req);
};
