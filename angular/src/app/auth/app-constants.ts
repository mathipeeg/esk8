export abstract class AppConstants {
  readonly geoserverUrl: string = '';
  readonly authenticationUrl: string = 'https://auth.esk8.grand-cloud.dk/auth/realms/esk8';
  readonly clientId: string = 'esk8';
  readonly adminRole: string = 'esk8-admin';
  readonly userRole: string = 'esk8-user';
  readonly appVersion: string = '';
  readonly authoritiesClaimName: string = 'roles';
}
