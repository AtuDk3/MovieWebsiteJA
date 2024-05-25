import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/movie';
import { environment } from '../environments/environments';


@Injectable({
  providedIn: 'root'
})
export class MovieService {
  
  private apiGetMovies = `${environment.apiBaseUrl}/movies`;
  
  constructor(private http: HttpClient) { }

  getMovies(page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams().set('page', page.toString()).set('limit', limit.toString());
    return this.http.get<Movie[]>(this.apiGetMovies, {params});
  }


}
