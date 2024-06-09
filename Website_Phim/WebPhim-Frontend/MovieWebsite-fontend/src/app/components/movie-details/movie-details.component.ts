import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';
import { UserResponse } from '../../responses/user/user.response';
import { UserService } from '../../services/user.service';
import { VipPeriodResponse } from '../../responses/user/vip_period.response';
import { UpdateUserDTO } from '../../dtos/user/updateuser.dto';
import { TokenService } from '../../services/token.service';
import { BookmarkService } from '../../services/bookmark.service';
import { FavouriteResponse } from '../../responses/user/favourite.response';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.scss'
})
export class MovieDetailsComponent implements OnInit {
  movie?: Movie;
  movieId: number = 0;
  userResponse?: UserResponse | null
  vipPeriodResponse?: VipPeriodResponse | null
  existError: string | null = null;
  message: string | null = null;
  moviesRelated: FavouriteResponse[] = [];
  currentPage: number = 0; 
  itemsPerPage: number = 4;
  totalPages: number = 0;
  visiblePages: number[] = []; 

  constructor(
    private movieService: MovieService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private tokenService: TokenService,
    private bookmarkService: BookmarkService) { }

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');

    if (idParam !== null) {
      this.movieId = +idParam;
    }
    if (!isNaN(this.movieId)) {
      this.movieService.getDetailMovie(this.movieId).subscribe({
        next: (response: any) => {
          this.movie = {
            ...response,
            release_date: new Date(response.release_date)
          };
          if (this.movie && this.movie.image) {
            this.movie.url = `${environment.apiBaseUrl}/movies/images/${this.movie.image}`;
          }
          if (this.movie && this.movie.release_date) {
            const releaseDate = new Date(this.movie.release_date);
            const day = releaseDate.getDate();
            const month = releaseDate.getMonth() + 1;
            const year = releaseDate.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            this.movie.release_date_formated = formattedDate;
          } else {
            console.log("Ngày phát hành không tồn tại.");
          }
        },
        error: (error: any) => {
          console.log(error);
        }
      })
      this.getMoviesRelated(this.movieId, this.currentPage, this.itemsPerPage);
    }
  }

  getMoviesRelated(movie_id: number, page: number, limit: number){   
    this.movieService.getMoviesRelated(movie_id, page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: FavouriteResponse) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });                
        this.moviesRelated = response.movies;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      error: (error: any) => {
        console.error('Error fetching movies by related:', error);
      }
    });
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 10;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);
  
    let startPage = Math.max(currentPage - halfVisiblePages, 0); // Bắt đầu từ 0
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages - 1); // Dựa vào totalPages
  
    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 0); // Bắt đầu từ 0
    }
  
    const visiblePages = [];
    for (let i = startPage; i <= endPage; i++) {
        visiblePages.push(i);
    }
  
    return visiblePages;
  }
  
  
    goToPage(page: number, event: Event) {
      event.preventDefault();
      if (page >= 0 && page <= this.totalPages - 1) { // Đảm bảo không vượt quá giới hạn trang
        this.currentPage = page;
        this.getMoviesRelated(this.movieId, this.currentPage, this.itemsPerPage);
      }
    }

  checkFee(isFee: number, movieId: number) {
    if (isFee === 1) {
      this.userResponse = this.userService.getUserResponseFromLocalStorage();
      if (this.userResponse) {
        if (this.userResponse.user_vip.name.includes('vip')) {
          this.vipPeriodResponse = this.userService.getVipPeriodResponseFromLocalStorage();
          if (this.vipPeriodResponse?.expiration_date) {

            const expirationDate = new Date(this.vipPeriodResponse.expiration_date);
            const currentDate = new Date();

            if (expirationDate < currentDate) {
              const updateUserDTO: UpdateUserDTO = {
                full_name: null,
                vip_name: 'Normal User'
              };

              this.userService.updateUserDetails(this.userResponse.id, updateUserDTO, this.tokenService.getToken()!)
                .subscribe({
                  next: (response: any) => {
                    this.userResponse = {
                      ...response,
                      date_of_birth: new Date(response.date_of_birth),
                      created_at: new Date(response.created_at)
                    };
                    if (this.userResponse) {
                      const day = ('0' + new Date(this.userResponse.created_at).getDate()).slice(-2);
                      const month = ('0' + (new Date(this.userResponse.created_at).getMonth() + 1)).slice(-2);
                      const year = new Date(this.userResponse.created_at).getFullYear();
                      const formattedDate = `${day}/${month}/${year}`;
                      this.userResponse.created_at_formatted = formattedDate;

                      const day_of_birth = ('0' + new Date(this.userResponse.date_of_birth).getDate()).slice(-2);
                      const month_of_birth = ('0' + (new Date(this.userResponse.date_of_birth).getMonth() + 1)).slice(-2);
                      const year_of_birth = new Date(this.userResponse.date_of_birth).getFullYear();
                      const formatted_of_birth = `${day_of_birth}/${month_of_birth}/${year_of_birth}`;
                      this.userResponse.date_of_birth_formatted = formatted_of_birth;
                      this.userService.removeUserFromLocalStorage();
                      this.userService.removeVipPeriodFromLocalStorage();
                      this.userService.saveUserResponseToLocalStorage(this.userResponse);
                      this.userService.deleteVipPeriod(this.userResponse.id, this.tokenService.getToken()!).subscribe(
                        {
                          next: (response: any) => {
                          },
                          complete: () => {
                          },
                          error: (err) => {
                            console.log('error');
                          }
                        });
                      //trang mua quyen
                      this.router.navigate(['/register']);
                    }

                  },
                  error: (error: any) => {
                    console.error('Error updating user movie-detail details', error);

                  },
                  complete: () => {
                    console.log('Update user details movie-detail request completed.');
                  }
                });
            } else {
              this.router.navigate([`/watching/${movieId}`])
            }
          } else {
            console.log('Người dùng lỗi user vip')
          }
        } else {
          this.router.navigate(['/register']);
          //trang mua quyen
        }
      } else {
        this.router.navigate(['/login']);
      }
    } else {
      this.router.navigate([`/watching/${movieId}`])     
    }

  }

  addMovieBookmark(movieId: number) {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    if (this.userResponse) {
      this.bookmarkService.addMovieFavourite(this.userResponse?.id, movieId, this.tokenService.getToken()!).subscribe({
        next: response => {
          this.message= response['message'];
          this.bookmarkService.incrementBookmarkCount();
        },
        error: err => {
          console.log('Error adding bookmark', err);
          this.message = null;
          if (err.error.existError) {
          this.existError = err.error.existError;
          }
        }
      });
    }
  }
}

