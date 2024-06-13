
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RateService } from '../../services/rate.service';
import { RateDTO } from '../../dtos/user/rate.dto';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent implements OnInit {
  isDialogOpen = false;
  rating = 0; // Giá trị mặc định
  hoverRating = 0; // Giá trị mặc định khi di chuột
  movieId: number = 0;
  userResponse: UserResponse | null = null;
  averageRating = 1.4; // Số trung bình sao (ví dụ)
  numberOfRatings = 100; // Số lượt đánh giá (ví dụ)
  
  constructor(
    private activatedRoute: ActivatedRoute,
    private rateService: RateService,
    private userService: UserService,
    private tokenService: TokenService,
    private router: Router
  ) { }

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.movieId = +idParam;
    }
  }

  // Các mô tả cho từng mức đánh giá
  ratingDescriptions: { [key: number]: string } = {
    1: 'Rất tệ',
    2: 'Tệ',
    3: 'Bình thường',
    4: 'Tốt',
    5: 'Xuất sắc'
  };

  get fullStars(): number {
    return Math.floor(this.averageRating);
  }

  get halfStarWidth(): string {
    const remainder = this.averageRating % 1;
    return `${Math.round(remainder * 100)}%`;
  }

  get emptyStars(): number {
    return 5 - Math.ceil(this.averageRating);
  }

  get ratingDescription(): string {
    return this.ratingDescriptions[this.rating];
  }

  openDialog(): void {
    this.isDialogOpen = true;
  }

  closeDialog(): void {
    this.isDialogOpen = false;
  }

  onRatingChange(newRating: number): void {
    this.rating = newRating;
  }

  onHover(newHoverRating: number): void {
    this.hoverRating = newHoverRating;
  }

  resetHover(): void {
    this.hoverRating = this.rating;
  }

  submitRating(): void {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();

    if (this.userResponse) {
      const rateDTO: RateDTO = {
        "movie_id": this.movieId,
        "user_id": this.userResponse.id,
        "number_stars": this.rating
      };
      this.rateService.createRateMovie(rateDTO, this.tokenService.getToken()!).subscribe({
        next: (response: any) => {
          this.closeDialog();
        },
        complete: () => {},
        error: (err) => {}
      });
    }
  }
}
