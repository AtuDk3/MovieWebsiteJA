import { Component, OnInit, AfterViewInit, AfterViewChecked } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import 'owl.carousel';

@Component({
  selector: 'app-movie-related',
  templateUrl: './movie-related.component.html',
  styleUrls: ['./movie-related.component.scss']
})
export class MovieRelatedComponent implements OnInit, AfterViewInit, AfterViewChecked {
  movies: Movie[] = [];
  currentPage: number = 0; 
  itemsPerPage: number = 6;
  totalPages: number = 0;
  visiblePages: number[] = []; 
  carouselInitialized: boolean = false;

  constructor(
    private movieService: MovieService,
  ) { }

  ngOnInit(): void {
    this.getHotMovies(this.currentPage, this.itemsPerPage);
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

  getHotMovies(page: number, limit: number) {
    this.movieService.getHotMovies(page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });

        this.movies = response.movies;
        console.log('Movies:', this.movies);  // Kiểm tra dữ liệu phim
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      error: (error: any) => {
        console.error('Error fetching movies by hot movie:', error);
      }
    });
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 0);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages - 1);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 0);
    }

    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }

  goToPage(page: number, event: Event) {
    event.preventDefault();
    if (page >= 0 && page <= this.totalPages - 1) {
      this.currentPage = page;
      this.getHotMovies(this.currentPage, this.itemsPerPage);
    }
  }
}
