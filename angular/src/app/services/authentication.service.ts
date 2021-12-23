import {Injectable} from '@angular/core';
import {OidcSecurityService} from 'angular-auth-oidc-client';
import {filter, map} from 'rxjs/operators';
import {BearerToken} from '@northtech/bragi';
import {BehaviorSubject, Observable} from 'rxjs';
import jwtDecode from 'jwt-decode';
import {LoginUser} from "../models";
import {HttpHeaders} from "@angular/common/http";

export enum Authorization {
  UNAUTHORIZED,
  USER,
  ADMIN
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private readonly checkAuthCompleteSubject = new BehaviorSubject<boolean>(false);
  readonly checkAuthComplete$ = this.checkAuthCompleteSubject.asObservable();
  readonly isAuthenticated$: Observable<boolean> =
    this.oidcSecurityService.isAuthenticated$
      .pipe(
        map(authenticationResult => authenticationResult.isAuthenticated)
      );
  readonly bearerToken$ =
    this.isAuthenticated$
      .pipe(
        map((authenticationResult) => (
          authenticationResult
            ? new BearerToken(() => this.oidcSecurityService.getAccessToken())
            : undefined
        ))
      );
  readonly authorization$: Observable<Authorization> =
    this.isAuthenticated$
      .pipe(
        filter(Boolean),
        map(() =>
          jwtDecode<Record<string, string[] | undefined>>(this.oidcSecurityService.getAccessToken())),
        map(token => <Record<string, string[]>> <unknown> token['resource_access']),
        map(token => <Record<string, string[]>> <unknown> token['esk8']),
        map(token => token['roles']),
        map(
          roles => {
            if (roles === undefined) {
              throw new Error(`Missing roles claims in token`);
            }
            if (roles.some(role => role === 'esk8-admin')) {
              return Authorization.ADMIN;
            }
            if (roles.some(role => role === 'esk8-user')) {
              return Authorization.USER;
            }

            return Authorization.UNAUTHORIZED;
          }
        )
      );
  readonly currentUser$: Observable<LoginUser> =
    this.isAuthenticated$
      .pipe(
        filter(Boolean),
        map(() =>
          jwtDecode<Record<string, string[] | undefined>>(this.oidcSecurityService.getAccessToken())),
        map(
          jwt => {
            console.log(jwt)
            return {
              referenceKey: jwt['sub'],
              username: jwt['preferred_username'],
              email: jwt['email'],
              name: jwt['given_name'],
              lastname: jwt['family_name']
            } as unknown as LoginUser;
          }
        )
      );

  constructor(
    private readonly oidcSecurityService: OidcSecurityService
  ) {
  }

  setCheckAuthComplete(): void {
    this.checkAuthCompleteSubject.next(true);
  }

  login(): void {
    this.oidcSecurityService.authorize();
  }

  logout(): void {
    this.oidcSecurityService.logoff();
  }

  getAccessToken(): HttpHeaders {
    return this.oidcSecurityService.isAuthenticated()
      ? new HttpHeaders({Authorization: 'Bearer ' + this.oidcSecurityService.getAccessToken()})
      : new HttpHeaders();
  }
}
