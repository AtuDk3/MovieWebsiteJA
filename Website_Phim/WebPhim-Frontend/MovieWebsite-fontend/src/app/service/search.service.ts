import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private keywordSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');
  public keyword$: Observable<string> = this.keywordSubject.asObservable();

  constructor() { }

  setKeyword(keyword: string) {
    this.keywordSubject.next(keyword);
  }

  getKeyword(): Observable<string> {
    return this.keyword$;
  }

  searchMovies() {
    // Your logic here
  }
}
