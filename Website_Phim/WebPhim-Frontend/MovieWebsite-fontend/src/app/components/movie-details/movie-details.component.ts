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
import { TopViewService } from '../../services/top_view.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

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


  constructor(
    private movieService: MovieService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private tokenService: TokenService,
    private bookmarkService: BookmarkService,
    private topViewService: TopViewService,
    private toastr: ToastrService) { }

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
          if (this.movie) {
            if (!this.movie.image.includes('http')) {
              this.movie.url = `${environment.apiBaseUrl}/movies/images/${this.movie.image}`;
            } else {
              this.movie.url = this.movie.image;
            }
          }
        },
        error: (error: any) => {
          console.log(error);
        }
      })
    }
  }


  checkFee(isFee: number, movieId: number, limit_age: number) {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    if (isFee === 1) {
      if (this.userResponse) {
        if (this.userResponse.user_vip.name.includes('vip')) {
          this.vipPeriodResponse = this.userService.getVipPeriodResponseFromLocalStorage();
          if (this.vipPeriodResponse?.expiration_date) {

            const expirationDate = new Date(this.vipPeriodResponse.expiration_date);
            const currentDate = new Date();

            if (expirationDate < currentDate) {
              const updateUserDTO: UpdateUserDTO = {
                full_name: '',
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
                      this.userService.removeUserFromLocalStorage();
                      this.userService.removeVipPeriodFromLocalStorage();
                      this.userService.saveUserResponseToLocalStorage(this.userResponse);
                      this.userService.deleteVipPeriod(this.userResponse.id, this.tokenService.getToken()!).subscribe(
                        {
                          next: (response: any) => {
                            this.router.navigate(['/upgrade-account']);
                          },
                          complete: () => {
                          },
                          error: (err) => {
                            console.log('error');
                          }
                        });
                      //trang mua quyen

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
          this.router.navigate(['/upgrade-account']);
          //trang mua quyen
        }
      } else {
        this.router.navigate(['/login']);
      }
    } else {
      if (this.userResponse) {
        const dob = new Date(this.userResponse.date_of_birth)
        const today = new Date();
        let age = today.getFullYear() - dob.getFullYear();
        const monthDifference = today.getMonth() - dob.getMonth();

        if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < dob.getDate())) {
          age--;
        }
        if (age >= limit_age) {
          this.router.navigate([`/watching/${movieId}`])
        } else {
          alert('Phim không phụ hợp với độ tuổi của bạn!');
        }
      } else {
        if (limit_age >= 18) {
          this.router.navigate(['']);
        } else {
          this.router.navigate([`/watching/${movieId}`])
        }
      }
    }
  }

  // incremnetViewMovie(movie_id:number){
  //   this.topViewService.incrementMovieView(movie_id).subscribe(
  //     {
  //       next: (response: any) => {
  //       },
  //       complete: () => {
  //       },
  //       error: (err) => {
  //         console.log('error increment view');
  //       }
  //     });
  // }

  addMovieBookmark(movieId: number) {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    if (this.userResponse) {
      this.bookmarkService.addMovieFavourite(this.userResponse?.id, movieId, this.tokenService.getToken()!).subscribe({
        next: response => {
          this.toastr.success('Đã thêm vào mục phim yêu thích!', 'Thêm thành công', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.bookmarkService.incrementBookmarkCount();
        },
        error: err => {
          console.log('Error adding bookmark', err);
          this.toastr.error('Đã có trong mục phim yêu thích!', 'Thêm thất bại', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }
}