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
  private apiGetMoviesByMovieType = `${environment.apiBaseUrl}/movies/movie_types`;
  private apiGetAllMoviesByMovieType = `${environment.apiBaseUrl}/movies/movie_types`;
  
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
    return this.http.get<Movie[]>(this.apiGetMoviesByGenre, {params});
  }

  getDetailMovie(movieId: number){
    return this.http.get(this.apiGetMovies+`/${movieId}`);
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

  getMoviesByMovieTypeId(movie_type_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString())
      .set('movie_type_id', movie_type_id.toString());

    return this.http.get<Movie[]>(this.apiGetMoviesByMovieType, { params });
  }

  getAllMoviesByMovieType(movie_type_id:number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('movie_type_id', movie_type_id.toString())
      .set('limit', limit.toString());

    return this.http.get<Movie[]>(this.apiGetMoviesByMovieType, { params });
  }

}
