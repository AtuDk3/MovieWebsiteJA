import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieType } from '../models/movie_type';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieTypeService {
  private apiGetMovieTypes = `${environment.apiBaseUrl}/movie_types`;

  constructor(private http: HttpClient) { }

  getMovieTypes(): Observable<MovieType[]> {
    return this.http.get<MovieType[]>(this.apiGetMovieTypes);
  }

  // Phương thức để lấy thông tin về một thể loại phim cụ thể dựa trên ID
  getMovieTypeById(movieTypeId: number): Observable<MovieType> {
    const url = `${this.apiGetMovieTypes}/${movieTypeId}`;
    return this.http.get<MovieType>(url);
  }

  // Phương thức để tạo một thể loại phim mới
  createMovieType(movieType: MovieType): Observable<MovieType> {
    return this.http.post<MovieType>(this.apiGetMovieTypes, movieType);
  }

  // Phương thức để cập nhật thông tin của một thể loại phim đã tồn tại
  updateMovieType(movieType: MovieType): Observable<MovieType> {
    const url = `${this.apiGetMovieTypes}/${movieType.id}`;
    return this.http.put<MovieType>(url, movieType);
  }

  // Phương thức để xóa một thể loại phim dựa trên ID
  deleteMovieType(movieTypeId: number): Observable<any> {
    const url = `${this.apiGetMovieTypes}/${movieTypeId}`;
    return this.http.delete<any>(url);
  }
}
