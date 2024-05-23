import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8088/api/v1'; // Cập nhật URL này theo cấu hình API của bạn

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post(`${this.baseUrl}/register`, user, { headers });
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/verify-email?token=${token}`);
  }
}