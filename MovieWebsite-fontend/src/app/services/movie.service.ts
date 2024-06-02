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
  
  constructor(private http: HttpClient) { }

    getAllMovies(keyword: string, genre_id: number, page: number, limit: number): Observable<Movie[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());
      
    if (keyword) {
      params = params.set('keyword', keyword);
    }
    
    if (genre_id) {
      params = params.set('genre_id', genre_id.toString());
    }
    return this.http.get<Movie[]>(this.apiGetMovies, {params});
  }

  getDetailMovie(movieId: number){
    return this.http.get(this.apiGetMovies+`/${movieId}`);
  }

  getMoviesByGenreId(genre_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('genre_id', genre_id.toString());

    return this.http.get<Movie[]>(this.apiGetMovies, { params });
  }

  getMoviesByCountryId(country_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('country_id', country_id.toString());

      console.log('Request URL:', this.apiGetMovies);
  console.log('Request Params:', params.toString());

    return this.http.get<Movie[]>(this.apiGetMovies, { params });
}

  getMoviesByMovieTypeId(movie_type_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('movie_type_id', movie_type_id.toString());

    return this.http.get<Movie[]>(this.apiGetMovies, { params });
  }

}
