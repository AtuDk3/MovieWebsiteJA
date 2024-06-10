import { Component, OnInit, AfterViewInit, AfterViewChecked } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import 'owl.carousel';
import { ActivatedRoute, Router } from '@angular/router';
import { UserResponse } from '../../responses/user/user.response';
import { UserService } from '../../services/user.service';
import { VipPeriodResponse } from '../../responses/user/vip_period.response';
import { UpdateUserDTO } from '../../dtos/user/updateuser.dto';
import { TokenService } from '../../services/token.service';
import { BookmarkService } from '../../services/bookmark.service';
import { FavouriteResponse } from '../../responses/user/favourite.response';

@Component({
  selector: 'app-movie-related',
  templateUrl: './movie-related.component.html',
  styleUrls: ['./movie-related.component.scss']
})
export class MovieRelatedComponent implements OnInit, AfterViewInit, AfterViewChecked {
  movies: Movie[] = [];
  currentPage: number = 0; 
  itemsPerPage: number = 10;
  carouselInitialized: boolean = false;
  moviesRelated: FavouriteResponse[] = [];
  movieId: number = 0;


  constructor(
    private movieService: MovieService,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');

    if (idParam !== null) {
      this.movieId = +idParam;
    }
  }

  ngAfterViewInit(): void {
    // Carousel initialization is handled in ngAfterViewChecked
  }

  ngAfterViewChecked(): void {
    if (this.movies.length && !this.carouselInitialized) {
      this.initOwlCarousel();
      this.carouselInitialized = true;
    }
  }

  initOwlCarousel(): void {
    $('#halim_related_movies-2').owlCarousel({
      loop: true,
      margin: 4,
      autoplay: true,
      autoplayTimeout: 4000,
      autoplayHoverPause: true,
      nav: true,
      navText: ['<i class="fa-solid fa-angles-left"></i>', '<i class="fa-solid fa-angles-right"></i>'],
      responsiveClass: true,
      responsive: {
        0: { items: 2 },
        480: { items: 3 },
        600: { items: 4 },
        1000: { items: 4 }
      }
    });
  }

  getMoviesRelated(movie_id: number, page: number, limit: number){   
    this.movieService.getMoviesRelated(movie_id, page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: FavouriteResponse) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });                
        this.moviesRelated = response.movies;

      },
      error: (error: any) => {
        console.error('Error fetching movies by related:', error);
      }
    });
  }
  
}
