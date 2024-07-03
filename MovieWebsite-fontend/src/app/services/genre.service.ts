import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre } from '../models/genre';
import { environment } from '../environments/environment';
import { GenreDTO } from '../dtos/user/genre.dto';

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  private apiGetGenres = `${environment.apiBaseUrl}/genres`;

  constructor(private http: HttpClient) { }

  getGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiGetGenres);
  }

  // Phương thức để lấy thông tin về một thể loại phim cụ thể dựa trên ID
  getGenreById(genreId: number): Observable<any> {
    const url = `${this.apiGetGenres}/${genreId}`;
    return this.http.get(url);
  }

  // Phương thức để tạo một thể loại phim mới
  createGenre(genre: Genre): Observable<Genre> {
    return this.http.post<Genre>(this.apiGetGenres, genre);
  }

  // Phương thức để cập nhật thông tin của một thể loại phim đã tồn tại
  updateGenre(genre: GenreDTO): Observable<Genre> {
    const url = `${this.apiGetGenres}/${genre.id}`;
    return this.http.put<Genre>(url, genre);
  }

  // Phương thức để xóa một thể loại phim dựa trên ID
  deleteGenre(genreId: number): Observable<any> {
    const url = `${this.apiGetGenres}/${genreId}`;
    return this.http.delete<any>(url);
  }
}