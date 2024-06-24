
import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';
@Injectable({
  providedIn: 'root'
})
export class AuthGGService {

    private oAuthService: OAuthService;

    constructor(oAuthService: OAuthService, ) {
      this.oAuthService = oAuthService;
      this.initLoginGG();
    }

 initLoginGG() {
    const config: AuthConfig = {
      issuer: 'https://accounts.google.com',
      strictDiscoveryDocumentValidation: false,
      clientId: '155822361355-4mcbcv266h8aof1056dvtk1udjeth901.apps.googleusercontent.com',
      redirectUri: 'http://localhost:4200/login-google',
      scope: 'openid profile email',
    };
    this.oAuthService.configure(config);
    this.oAuthService.setupAutomaticSilentRefresh();
    this.oAuthService.loadDiscoveryDocumentAndTryLogin();
  }

  loginGG() {
    this.oAuthService.initLoginFlow();
  }

  getProfile() {
    return this.oAuthService.getIdentityClaims();
  }


}