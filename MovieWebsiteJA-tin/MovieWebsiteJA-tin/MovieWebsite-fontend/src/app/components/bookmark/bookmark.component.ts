import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { FavouriteResponse } from '../../responses/user/favourite.response';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../services/token.service';
import { BookmarkService } from '../../services/bookmark.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-bookmark',
  templateUrl: './bookmark.component.html',
  styleUrl: './bookmark.component.scss'
})
export class BookmarkComponent implements OnInit {

  favouriteResponse: FavouriteResponse[] = [];
  userResponse: UserResponse | null = null;

  isData: boolean = false;

  constructor(private userService: UserService,
    private tokenService: TokenService,
    private bookmarkServive: BookmarkService,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    this.load();
  }
  load() {
    if (this.userResponse) {
      this.bookmarkServive.getMovieFavourite(this.userResponse.id, this.tokenService.getToken()!).subscribe({
        next: (response: any) => {
          console.log(response)
          if (response && response.length > 0) {
            this.isData = true;
            response.forEach((favourite: FavouriteResponse) => {
              favourite.url = `${environment.apiBaseUrl}/movies/images/${favourite.image}`;
              this.favouriteResponse = response;
            });

          } else {
            this.isData = false;
          }
        },
        error: (error: any) => {
          console.log(error);
        }

      });

    }
  }

  deleteFavourite(movie_id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will remove the movie from your bookmarks!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        if (this.userResponse) {
          this.bookmarkServive.deleteMovieFavourite(this.userResponse.id, movie_id, this.tokenService.getToken()!).subscribe(
            {
              next: (response: any) => {
                this.toastr.success('Movie deleted successfully!', 'Delete Success', {
                  timeOut: 3000,
                  positionClass: 'toast-bottom-right'
                }); 
                this.bookmarkServive.subtractBookmarkCount();
                this.load();
              },
              complete: () => {
              },
              error: (err) => {
                this.toastr.error('There was a problem movie deleted!', 'Deleted Failed', {
                  timeOut: 3000,
                  positionClass: 'toast-bottom-right'
                }); 
                console.log('error delete favourite');
              }
            });
        }
      }
    });

  }



}