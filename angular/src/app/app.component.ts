import { Component } from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";
import {OidcSecurityService} from "angular-auth-oidc-client";

@Component({
  selector: 'nt-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'esk8';

  constructor(
    private readonly oidcSecurityService: OidcSecurityService,
    private readonly authenticationService: AuthenticationService
  ) {
  }



  ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe({complete: () => this.authenticationService.setCheckAuthComplete()});
  }
}
