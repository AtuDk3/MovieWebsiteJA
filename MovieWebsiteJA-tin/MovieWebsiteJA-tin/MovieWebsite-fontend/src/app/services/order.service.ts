
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { OrderDTO } from '../dtos/user/order.dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  
  private apiOrders = `${environment.apiBaseUrl}/orders`;
  private apiGetAllOrders = `${environment.apiBaseUrl}/orders/all_orders`;
  private apiGetAllOrderByUser = `${environment.apiBaseUrl}/orders/order_by_user`;
  private apiGetTotalPriceByMonth = `${environment.apiBaseUrl}/orders/order_by_month`;
  private apiGetYear = `${environment.apiBaseUrl}/orders/years`;

  constructor(private http: HttpClient) { }

  private apiConfig = {
    headers: this.createHeader(),
  }

  private createHeader(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      //'Accepted-Language': 'en'
    });
  }

  createOrder(orderDTO: OrderDTO, token: string): Observable<any> {   
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.post(this.apiOrders, orderDTO, { headers: headers });
  }

  getOrders(page: number, limit: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());
      
    return this.http.get(this.apiGetAllOrders, {params});
  }

  getOrderByUser(user_id: number, page: number, limit: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('user_id', user_id.toString());;      
    return this.http.get(this.apiGetAllOrderByUser, {params});
  }

  getOrderByMonth(year: number): Observable<any> {
    let params = new HttpParams()
      .set('year', year.toString());      
    return this.http.get(this.apiGetTotalPriceByMonth, {params});
  }

  getYears(): Observable<number[]> {
    return this.http.get<number[]>(`${this.apiGetYear}`);
  }

  getOrderStatistics(): Observable<any>{
    return this.http.get(this.apiGetAllOrders);
  }

}
