import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
    const params = new HttpParams()
      .set('movie_id', movie_id)   
    return this.http.post(this.apiTopView, { params });
  }

  updateViewForDay(): Observable<any> {
    return this.http.get(this.apiTopView);
  }

  //Admin
  deleteOldViewMonth(): Observable<any> {
    return this.http.delete(this.apiDeleteOldViewMonth);
  }



}