import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environments';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiConfig = {
    headers: this.createHeader(),
  }
  
  constructor(private http: HttpClient) { }

  private createHeader():HttpHeaders {
    return new HttpHeaders({
          'Content-Type': 'application/json',
        });
  }

  register(registerData: any): Observable<any> {
    return this.http.post(this.apiRegister, registerData, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.apiLogin, loginDTO, this.apiConfig);
  }


}
