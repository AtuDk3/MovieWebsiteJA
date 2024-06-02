import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-theaters-movie',
  templateUrl: './theaters-movie.component.html',
  styleUrl: './theaters-movie.component.scss'
})
export class TheatersMovieComponent implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 0; 
  itemsPerPage: number = 6;
  totalPages: number = 0;
  visiblePages: number[] = []; 
  keyword: string = '';
  movie_type_id: number = 0;
  movieTypeName: string = '';

  constructor(
    private movieService: MovieService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.movie_type_id = +params['movie_type_id'];
      this.getMoviesByMovieTypeId(this.movie_type_id, this.currentPage, this.itemsPerPage);
    });
  }

  getMoviesByMovieTypeId(genre_id: number, page: number, limit: number) {
    this.movieService.getMoviesByMovieTypeId(genre_id, page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
          this.movieTypeName = response.movie_type_name;
        });

        this.movies = response.movies;
        this.movieTypeName = response.movies.length > 0 ? response.movies[0].movie_type_name : '';
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      error: (error: any) => {
        console.error('Error fetching movies by movie type:', error);
      }
    });
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 0); // Bắt đầu từ 0
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages - 1); // Dựa vào totalPages

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 0); // Bắt đầu từ 0
    }

    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }

  goToPage(page: number, event: Event) {
    event.preventDefault();
    if (page >= 0 && page <= this.totalPages - 1) { // Đảm bảo không vượt quá giới hạn trang
      this.currentPage = page;
      this.getMoviesByMovieTypeId(this.movie_type_id, this.currentPage, this.itemsPerPage);
    }
  }
}
