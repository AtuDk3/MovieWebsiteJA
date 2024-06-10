import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { VNPayResponse } from '../responses/user/vn_pay.response';

@Injectable({
  providedIn: 'root'
})
export class VnpayService {
  private apiUrl = `${environment.apiBaseUrl}/payments`;

  constructor(private http: HttpClient) { }

  createPayment(amount: string, bankCode: string): Observable<VNPayResponse> {

    // Tạo các tham số của yêu cầu HTTP
    const params = new HttpParams()
      .set('amount', amount)
      .set('bankCode', bankCode);

    // Gửi yêu cầu HTTP với các tham số đã mã hóa
    return this.http.get<VNPayResponse>(`${this.apiUrl}/vn_pay`, { params });
  }

}
