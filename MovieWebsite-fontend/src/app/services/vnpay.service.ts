import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VnpayService {
  private apiUrl = `${environment.apiBaseUrl}/payments`;

  constructor(private http: HttpClient) { }

  createPayment(amount: number, orderInfo: string): Observable<string> {
    const params = new HttpParams()
      .set('amount', amount)
      .set('orderInfo', orderInfo);

    return this.http.post(`${this.apiUrl}/create_order`, null, { params, responseType: 'text' });
  }

  // paymentCompleted(): Observable<string> {
  //   return this.http.get(`${this.apiUrl}/vnpay_payment_return`, { responseType: 'text' });
  // }
}
