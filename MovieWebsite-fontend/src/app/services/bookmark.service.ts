import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})
export class BookmarkService {
  private readonly STORAGE_KEY = 'bookmarkCount';
  // public DEFAULT_COUNT=0; // Set your default initial value here
  // private bookmarkCountSource = new BehaviorSubject<number>(this.DEFAULT_COUNT);
  // currentBookmarkCount = this.bookmarkCountSource.asObservable();
  private bookmarkCountSource: BehaviorSubject<number>;
  currentBookmarkCount: Observable<number>;


  private apiBookMark = `${environment.apiBaseUrl}/favourites`;
  

  // constructor(private http: HttpClient) { 
  //   const savedCount = localStorage.getItem(this.STORAGE_KEY);
  //   if (savedCount !== null) {
  //     this.bookmarkCountSource.next(Number(savedCount));
  //   } else {
  //     this.bookmarkCountSource.next(this.DEFAULT_COUNT);
  //     localStorage.setItem(this.STORAGE_KEY, this.DEFAULT_COUNT.toString());
  //   }   
  // }

  constructor(
    private http: HttpClient, 
    private authService: AuthService
  ) {
    this.bookmarkCountSource = new BehaviorSubject<number>(0); // Khởi tạo với giá trị 0
    this.currentBookmarkCount = this.bookmarkCountSource.asObservable();
    //Lắng nghe sự kiện đăng nhập từ AuthService
    this.authService.isLoggedIn$.subscribe(isLoggedIn => {
      if (isLoggedIn) {     
        const userId = this.authService.getUserId();
        if (userId !== null) {
          this.initializeBookmarkCount(userId);
        }
      }
    });
  }

  private initializeBookmarkCount(user_id: number): void {
    const savedCount = localStorage.getItem(this.STORAGE_KEY);
    if (savedCount !== null) {
      this.bookmarkCountSource.next(Number(savedCount));
    }else{    
      this.http.get<number>(`${this.apiBookMark}/${user_id}/count`).subscribe({
      next: (count: number) => {  
          this.bookmarkCountSource.next(count);
          localStorage.setItem(this.STORAGE_KEY, count.toString());       
      },
      error: (error: any) => {
        console.error('Error fetching bookmark count:', error);
      }
    });

  }  
 }  


  incrementBookmarkCount() {
    const newCount = this.bookmarkCountSource.value + 1;
    this.bookmarkCountSource.next(newCount);
    localStorage.setItem(this.STORAGE_KEY, newCount.toString());
  }

  subtractBookmarkCount() {
    const newCount = this.bookmarkCountSource.value - 1;
    this.bookmarkCountSource.next(newCount);
    localStorage.setItem(this.STORAGE_KEY, newCount.toString());
  }

  resetBookmarkCount() {
    localStorage.removeItem(this.STORAGE_KEY)
  }

  addMovieFavourite(user_id: number,movie_id: number, token: string): Observable<any> {   
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post(this.apiBookMark,{user_id, movie_id}, { headers: headers });
  }

  getMovieFavourite(user_id: number, token: string): Observable<any> {
    const url = `${this.apiBookMark}/${user_id}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.get(url, { headers: headers });
  }

  deleteMovieFavourite(user_id: number, movie_id: number, token: string): Observable<any> {
    const url = `${this.apiBookMark}/${user_id}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post(url, {movie_id}, { headers: headers });
  }


}