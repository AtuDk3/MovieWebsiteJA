import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { CommentDTO } from '../dtos/user/comment.dto';


@Injectable({
    providedIn: 'root'
})
export class CommentService {

    private apiComment = `${environment.apiBaseUrl}/comments`;
    
    constructor(private http: HttpClient) { }

    getComments(movieId: number, page: number, limit: number): Observable<any> {
        const url = `${this.apiComment}/${movieId}`;
        let params = new HttpParams()
        .set('page', page.toString())
        .set('limit', limit.toString())
        return this.http.get(url,{params});
    }

    createComment(commentDTO: CommentDTO): Observable<any> {
        return this.http.post(this.apiComment, commentDTO);
    }
    
}