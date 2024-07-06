import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class AccountService {
    private apiListAccount = `${environment.apiBaseUrl}/accounts/list_account`;
    private apiBanAccount = `${environment.apiBaseUrl}/accounts/ban_account`;
    private apiUnBanAccount = `${environment.apiBaseUrl}/accounts/unban_account`;

    constructor(private http: HttpClient) { }


    getListAccount(): Observable<any> {
       return this.http.get(this.apiListAccount);
    }

    banAccount(user_id: number): Observable<any> {
      let params = new HttpParams().set('user_id', user_id.toString());
      return this.http.get(this.apiBanAccount, { params });
    }

    unBanAccount(user_id: number): Observable<any> {
      let params = new HttpParams().set('user_id', user_id.toString());
      return this.http.get(this.apiUnBanAccount, { params });
    }
}  