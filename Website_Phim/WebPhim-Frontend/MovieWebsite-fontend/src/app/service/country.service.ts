import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Country } from '../models/country';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private apiGetCountries = `${environment.apiBaseUrl}/countries`;

  constructor(private http: HttpClient) { }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(this.apiGetCountries);
  }
}
