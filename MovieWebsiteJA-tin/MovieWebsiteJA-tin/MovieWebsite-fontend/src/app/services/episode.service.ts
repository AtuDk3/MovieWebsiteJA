import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { EpisodeDTO } from '../dtos/user/episode.dto';


@Injectable({
  providedIn: 'root'
})
export class EpisodeService {
  
  private apiGetMovies = `${environment.apiBaseUrl}/episodes`;
  private apiGetEpisodesByMovie = `${environment.apiBaseUrl}/episodes/list-episode-by-movie`;
  private apiUpdateEpisodesByMovie = `${environment.apiBaseUrl}/episodes/update_episode`;
  
  constructor(private http: HttpClient) { }

  getWatchingMovie(movieId: number){
    return this.http.get(`${environment.apiBaseUrl}/episodes/movies/${movieId}`);
  }

  getEpisodeByMovie(movie_id: number): Observable<any>{
    const url = `${this.apiGetEpisodesByMovie}/${movie_id}`;
    return this.http.get(url);
  }

  addEpisode(episodeDTO: EpisodeDTO){   
    return this.http.post(this.apiGetMovies, episodeDTO);
  }

  getInfoEpisode(episode_id: number): Observable<any>{
    return this.http.get(`${environment.apiBaseUrl}/episodes/${episode_id}`);
  }

  updateEpisode(id: number, episodeDTO: EpisodeDTO){
    const url = `${this.apiUpdateEpisodesByMovie}/${id}`;
    return this.http.put(url, episodeDTO);
  }

  deleteEpisode(episode_id: number): Observable<any>{
    return this.http.delete(`${environment.apiBaseUrl}/episodes/${episode_id}`);
  }
}
