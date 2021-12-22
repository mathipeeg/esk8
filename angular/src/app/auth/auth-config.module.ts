import { NgModule } from '@angular/core';
import { AuthModule, LogLevel} from 'angular-auth-oidc-client';

@NgModule({
  imports: [
    AuthModule.forRoot({
      config: {
        authority: 'http://localhost:8082/auth/realms/esk8',
        redirectUrl: window.location.origin + '/settings',
        postLogoutRedirectUri: window.location.origin,
        clientId: 'esk8',
        scope: 'openid profile',
        responseType: 'code',
        silentRenew: true,
        useRefreshToken: true,
        logLevel: LogLevel.Warn,
      },
    }),
  ],
  exports: [AuthModule],
})
export class AuthConfigModule {
}
