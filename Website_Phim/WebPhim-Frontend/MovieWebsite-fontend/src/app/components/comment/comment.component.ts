import { Component, OnInit, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentService } from '../../services/comment.service';
import { CommentResponse } from '../../responses/user/comment.response';
import { UserResponse } from '../../responses/user/user.response';
import { UserService } from '../../services/user.service';
import { DomSanitizer } from '@angular/platform-browser';
import { FormControl, Validators } from '@angular/forms';
import { CommentDTO } from '../../dtos/user/comment.dto';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.scss'
})
export class CommentComponent implements OnInit {

  movieId: number = 0;
  commentRes: CommentResponse[] = [];
  contentControl: FormControl;
  userResponse: UserResponse | null = null;
  submitted = false;
  currentPage: number = 0;
  itemsPerPage: number = 5;

  constructor(private commentService: CommentService, private activatedRoute: ActivatedRoute,
    private userService: UserService, private sanitizer: DomSanitizer,
    private router: Router
  ) {
    this.contentControl = new FormControl('', Validators.required);
  }

  ngOnInit(): void {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    if (this.userResponse) {
      this.loadImage(this.userResponse)
    }
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.movieId = +idParam;
      this.getComments(this.movieId);
    }
  }

  getComments(movieId: number) {
    this.currentPage=0;
    this.commentService.getComments(movieId, this.currentPage, this.itemsPerPage).subscribe({
      next: (response: any) => {
        response.comments.forEach((comment: CommentResponse) => {
          this.loadImage(comment.user_response);
        });      
        this.commentRes = response.comments;       
      },
      error: (error: any) => {
        console.error('Error fetching movies by related:', error);
      }
    });
  }

  loadImage(user: UserResponse) {
    this.userService.getImage(user.img_avatar).subscribe({
      next: (imageBlob: Blob) => {
        const objectURL = URL.createObjectURL(imageBlob);
        user.image_url = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      error: (error) => {
        console.error('Error loading image', error);
      },
      complete: () => {
        console.log('Image loading complete for user', user.id);
      }
    });
  }

  onSubmit() {
    this.submitted = true;
    if (this.contentControl.valid) {
      if (this.userResponse) {
        const newComment: CommentDTO = {
          movie_id: this.movieId,
          description: this.contentControl.value,
          user_id: this.userResponse.id
        };
        this.commentService.createComment(newComment).subscribe({
          next: (data: any) => {  
            this.getComments(this.movieId);
            this.contentControl.reset();
            this.submitted = false;      
          },
          error: (error) => {
            console.error('Error loading more comments:', error);
          }
        });          
      }
    }
  }

  checkBeforeComment() {
    if (!this.userResponse) {
      this.router.navigate(['/login']);
    }
  }

  // Implementing infinite scrolling
  @HostListener('window:scroll', ['$event'])
  onWindowScroll(event: any) {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 10) {
      this.loadMoreComments();
    }
  }

  loadMoreComments() {
    this.currentPage++;
    this.commentService.getComments(this.movieId, this.currentPage, this.itemsPerPage)
      .subscribe({
        next: (data: any) => {  
          data.comments.forEach((comment: CommentResponse) => {
            this.loadImage(comment.user_response);
          });   
            this.commentRes = [...this.commentRes, ...data.comments];               
        },
        error: (error) => {
          console.error('Error loading more comments:', error);
        }
      });
  }
    
}