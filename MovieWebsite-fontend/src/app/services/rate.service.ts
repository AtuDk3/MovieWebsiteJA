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


  constructor(private http: HttpClient) { }

  createRateMovie(rateDTO: RateDTO, token: string): Observable<any> {   
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post(this.apiRateMovie, rateDTO, { headers: headers });
  }


}