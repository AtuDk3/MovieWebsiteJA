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
  private apiGetHotMovies = `${environment.apiBaseUrl}/movies/movie-hot`;
  private apiGetImageMovie = `${environment.apiBaseUrl}/movies/upload_movie`;
  private apiGetMoviesRelated = `${environment.apiBaseUrl}/movies/related_movies`;
  
  constructor(private http: HttpClient) { }

  getMovies(page: number, limit: number): Observable<Movie[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());
      
    return this.http.get<Movie[]>(this.apiGetMovies, {params});
  }

  getMovieById(movieId: number): Observable<Movie> {
    const url = `${this.apiGetMovies}/${movieId}`;
    return this.http.get<Movie>(url);
  }

  createMovie(movie: Movie): Observable<Movie> {
    return this.http.post<Movie>(this.apiGetMovies, movie);
  }

  updateMovie(movie: Movie): Observable<Movie> {
    const url = `${this.apiGetMovies}/${movie.id}`;
    return this.http.put<Movie>(url, movie);
  }

  updateImageMovie(movieId: number, formData: FormData): Observable<Movie> {
    const url = `${this.apiGetImageMovie}/${movieId}`;
    return this.http.put<Movie>(url, formData);
}

  deleteMovie(movieId: number): Observable<any> {
    const url = `${this.apiGetMovies}/${movieId}`;
    return this.http.delete<any>(url);
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

  getHotMovies(page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());

    return this.http.get<Movie[]>(this.apiGetHotMovies, { params });
  }

  getMoviesRelated(movie_id: number, page: number, limit: number): Observable<Movie[]> {
    const params = new HttpParams()
      .set('page', page)
      .set('limit', limit)
      .set('movie_id', movie_id.toString())

    return this.http.get<Movie[]>(this.apiGetMoviesRelated, { params });
  }

  getMoviesByNumberViews(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.apiGetMovies);
  }

  getImage(imageName: string): Observable<Blob> {
    return this.http.get(`${this.apiGetImageMovie}/${imageName}`, { responseType: 'blob' });
  }

  uploadImageMovie(movieId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(`${this.apiGetMovies}/upload_movie/${movieId}`, formData);
  }

}
