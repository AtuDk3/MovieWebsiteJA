import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private userIdSource = new BehaviorSubject<number | null>(null);
  currentUserId: Observable<number | null> = this.userIdSource.asObservable();

  public isLoggedInSource = new BehaviorSubject<boolean>(false);
  isLoggedIn$: Observable<boolean> = this.isLoggedInSource.asObservable();

  constructor(private tokenService: TokenService) {}

  initialize(): Promise<void> {
    return new Promise((resolve) => {    
      const userId = this.tokenService.getUserId(); 
      if (userId) {
        this.setUserId(userId);
        this.isLoggedInSource.next(true);
      }
      resolve();
    });
  }

  setUserId(userId: number) {
    this.userIdSource.next(userId);
  }

  getUserId(): number | null {
    return this.userIdSource.value;
  }

}
