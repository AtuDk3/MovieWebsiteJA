import { Component, OnInit, AfterViewInit, AfterViewChecked } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import 'owl.carousel';
import { ActivatedRoute } from '@angular/router';
import { FavouriteResponse } from '../../responses/user/favourite.response';

@Component({
  selector: 'app-movie-related',
  templateUrl: './movie-related.component.html',
  styleUrls: ['./movie-related.component.scss']
})
export class MovieRelatedComponent implements OnInit, AfterViewInit, AfterViewChecked {
  
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
    console.log(idParam)
    if (idParam !== null) {
      this.movieId = +idParam;
      this.getMoviesRelated(this.movieId, this.currentPage, this.itemsPerPage);
    }
  }

  ngAfterViewInit(): void {
    // Carousel initialization is handled in ngAfterViewChecked
  }

  ngAfterViewChecked(): void {
    if (this.moviesRelated.length && !this.carouselInitialized) {
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
