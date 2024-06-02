import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from '../models/movie';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class EpisodeService {
  
  private apiGetMovies = `${environment.apiBaseUrl}/episodes`;
  
  constructor(private http: HttpClient) { }

  getWatchingMovie(movieId: number){
    return this.http.get(`${this.apiGetMovies}/movies/${movieId}`);
  }

}
