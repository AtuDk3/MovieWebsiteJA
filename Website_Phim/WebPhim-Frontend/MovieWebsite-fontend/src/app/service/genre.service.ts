import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre } from '../models/genre';
import { environment } from '../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private apiGetGenres = `${environment.apiBaseUrl}/genres`;

  constructor(private http: HttpClient) { }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiGetGenres);
  }
}
