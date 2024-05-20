import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiUrl = "http://localhost:8080/api/v1/users/register";
  constructor(private http: HttpClient) { }
  register(registerData: any): Observable<any> {
    debugger
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.http.post(this.apiUrl, registerData, {headers: headers});
  }
}
