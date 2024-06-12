import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VnpayService {

  private apiUrl = `${environment.apiBaseUrl}/vnpay`;

  constructor(private http: HttpClient) { }

  createPayment(orderId: string, amount: string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/create-payment`, { orderId, amount });
  }
}
