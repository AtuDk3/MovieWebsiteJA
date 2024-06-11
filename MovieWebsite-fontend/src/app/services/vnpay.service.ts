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

  createPayment(amount: string, orderInfo: string): Observable<string> {
    // Tạo các tham số của yêu cầu HTTP
    const params = new HttpParams()
      .set('amount', amount)
      .set('orderInfo', orderInfo);

    // Gửi yêu cầu HTTP POST với các tham số dạng query parameters
    return this.http.post(`${this.apiUrl}/create_order`, null, { params, responseType: 'text' });
  }
}
