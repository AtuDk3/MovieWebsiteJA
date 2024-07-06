import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MasterService {

  private apiUrl = 'https://api.example.com/chartdata'; // URL của API của bạn

  constructor(private http: HttpClient) { }

  Getchartinfo(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
