

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TopViewService {
  
  private apiTopView = `${environment.apiBaseUrl}/movie_views`;
  private apiTopViewByDay = `${environment.apiBaseUrl}/movie_views/top_view_day`;
  private apiTopViewByWeek = `${environment.apiBaseUrl}/movie_views/top_view_week`;
  private apiTopViewByMonth = `${environment.apiBaseUrl}/movie_views/top_view_month`;
  private apiDeleteOldViewMonth = `${environment.apiBaseUrl}/movie_views/delete_old_view`;
  private apiGetLastDeleteView = `${environment.apiBaseUrl}/movie_views/last_delete_view`;

  
  
  constructor(private http: HttpClient) { }

  getTopViewByDay(): Observable<any> {
    return this.http.get(this.apiTopViewByDay);
  }

  getTopViewByWeek(): Observable<any> {
    return this.http.get(this.apiTopViewByWeek);
  }

  getTopViewByMonth(): Observable<any> {
    return this.http.get(this.apiTopViewByMonth);
  }

  incrementMovieView(movie_id: number): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiTopView, { movie_id }, { headers });
  }

  updateViewForDay(): Observable<any> {
    return this.http.get(this.apiTopView);
  }

  //Admin
  deleteOldViewMonth(): Observable<any> {
    return this.http.delete(this.apiDeleteOldViewMonth);
  }

  getGetLastDeleteView(): Observable<any> {
    return this.http.get(this.apiGetLastDeleteView);
  }



}
