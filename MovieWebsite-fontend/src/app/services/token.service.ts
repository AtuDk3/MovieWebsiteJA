
import { Injectable } from '@angular/core';
import  {jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly TOKEN_KEY = 'access_token';
  constructor(){

  }
  getToken(): string | null { 
    return localStorage.getItem(this.TOKEN_KEY);
    }
    setToken(token: string): void {
        localStorage.setItem(this.TOKEN_KEY, token);
    }

    removeToken(): void {
        localStorage.removeItem(this.TOKEN_KEY);
    }

    isAuthenticated(): boolean {
        return this.getToken()!== null;
    }

    isTokenExpired(): boolean {
      const token = this.getToken();
      if (!token) {
        return true;
      }
  
      const decodedToken: any = jwtDecode(token);
      if (!decodedToken.exp) {
        return true;
      }
  
      const expiryTime = decodedToken.exp * 1000;
      return Date.now() > expiryTime;
    }

    getUserId(): number | null {
      const token = this.getToken();
      if (!token) {
        return null;
      }
  
      const decodedToken: any = jwtDecode(token);
      return decodedToken.id || null;  // Adjust 'userId' based on your token structure
    }
  


}
