import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Country } from '../models/country';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private apiGetCountries = `${environment.apiBaseUrl}/countries`;

  constructor(private http: HttpClient) { }

  getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(this.apiGetCountries);
  }

  // Phương thức để lấy thông tin về một quốc gia phim cụ thể dựa trên ID
  getCountryById(countryId: number): Observable<Country> {
    const url = `${this.apiGetCountries}/${countryId}`;
    return this.http.get<Country>(url);
  }

  // Phương thức để tạo một quốc gia phim mới
  createCountry(country: Country): Observable<Country> {
    return this.http.post<Country>(this.apiGetCountries, country);
  }

  // Phương thức để cập nhật thông tin của một quốc gia phim đã tồn tại
  updateCountry(country: Country): Observable<Country> {
    const url = `${this.apiGetCountries}/${country.id}`;
    return this.http.put<Country>(url, country);
  }

  // Phương thức để xóa một thể loại phim dựa trên ID
  deleteCountry(countryId: number): Observable<any> {
    const url = `${this.apiGetCountries}/${countryId}`;
    return this.http.delete<any>(url);
  }
}
