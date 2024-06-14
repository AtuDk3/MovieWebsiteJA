
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { RateDTO } from '../dtos/user/rate.dto';


@Injectable({
  providedIn: 'root'
})
export class RateService {
  
  private apiRateMovie = `${environment.apiBaseUrl}/rates`;
  private apiInfoRate = `${environment.apiBaseUrl}/rates/information_rate`;
  private apiDeleteOldRateMonth= `${environment.apiBaseUrl}/rates/delete_old_rate`;
  private apiGetLastDeleteRate= `${environment.apiBaseUrl}/rates/last_delete_rate`;

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

  createRateMovie(rateDTO: RateDTO, token: string): Observable<any> {   
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post(this.apiRateMovie, rateDTO, { headers: headers });
  }

  getRate(movie_id: number): Observable<any> {
    return this.http.post(this.apiInfoRate, {movie_id})    
  }

  //Admin
  deleteOldRateMonth(): Observable<any> {
    return this.http.delete(this.apiDeleteOldRateMonth);
  }

  getGetLastDeleteRate(): Observable<any> {
    return this.http.get(this.apiGetLastDeleteRate);
  }


}
