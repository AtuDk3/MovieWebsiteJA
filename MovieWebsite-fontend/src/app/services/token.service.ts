import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TokenService {
  private readonly  TOKEN_KEY = 'access_token';
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
  


}
