import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/movie';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class MovieService {
  
  private apiGetMovies = `${environment.apiBaseUrl}/movies`;
  private apiGetMoviesByGenre = `${environment.apiBaseUrl}/movies/genres`;
  private apiGetMoviesByCountry = `${environment.apiBaseUrl}/movies/countries`;
  
  constructor(private http: HttpClient) { }

  getMovies(keyword: string, genre_id: number, page: number, limit: number): Observable<Movie[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());
      
    if (keyword) {
      params = params.set('keyword', keyword);
    }
    
    if (genre_id) {
      params = params.set('genre_id', genre_id.toString());
    }
    return this.http.get<Movie[]>(this.apiGetMoviesByGenre, {params});
  }

  getDetailMovie(movieId: number){
    return this.http.get(`${environment.apiBaseUrl}/movies/${movieId}`);
  }

  getMoviesByGenreId(genre_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('genre_id', genre_id.toString());

    return this.http.get<Movie[]>(this.apiGetMoviesByGenre, { params });
  }

  getMoviesByCountryId(country_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('country_id', country_id.toString());

    return this.http.get<Movie[]>(this.apiGetMoviesByCountry, { params });
  }

}
