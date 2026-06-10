import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { appConfig } from './app.config';

const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(),
    provideHttpClient(withFetch()) // <-- ajoute ça
  ]
};

export const config = mergeApplicationConfig(appConfig, serverConfig);