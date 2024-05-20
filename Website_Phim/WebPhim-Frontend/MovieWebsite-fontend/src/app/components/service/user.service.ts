import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiUrl = "http://localhost:8088/api/v1/users/register";
  private apiLogin = "http://localhost:8088/api/v1/users/register";
  constructor(private http: HttpClient) { }
  register(registerData: any): Observable<any> {
    
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.http.post(this.apiUrl, registerData, {headers});
  }

  login(loginData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.http.post(this.apiLogin, loginData, {headers});
  }
}
