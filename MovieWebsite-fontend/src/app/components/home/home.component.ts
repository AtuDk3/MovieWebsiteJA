import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 20;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 
  keyword: string = '';
  genre_id: number = 0;

  constructor(private movieService: MovieService, private  router: Router) {}

  ngOnInit() {
    this.getAllMovies(this.keyword, this.genre_id, this.currentPage, this.itemsPerPage);
  }

  getAllMovies(keyword: string, genre_id: number, page: number, limit: number) {
    debugger
    this.movieService.getAllMovies(keyword, genre_id, page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });
        debugger
        this.movies = response.movies;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
        console.log(this.movies);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  // getMoviesByGenreId(genre_id: number, page: number, limit: number) {
  //   this.movieService.getMoviesByGenreId(genre_id, page, limit).subscribe({
  //     next: (response: any) => {
  //       response.movies.forEach((movie: Movie) => {
  //         movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
  //       });
  //       this.movies = response.movies;
  //       this.totalPages = response.totalPages;
  //       this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
  //       console.log(this.movies);
  //     },
  //     error: (error: any) => {
  //       console.error('Error fetching movies by genre:', error);
  //     }
  //   });
  // }

  // getMoviesByCountryId(country_id: number, page: number, limit: number) {
  //   this.movieService.getMoviesByCountryId(country_id, page, limit).subscribe({
  //     next: (response: any) => {
  //       response.movies.forEach((movie: Movie) => {
  //         movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
  //       });
  //       this.movies = response.movies;
  //       this.totalPages = response.totalPages;
  //       this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
  //       console.log(this.movies);
  //     },
  //     error: (error: any) => {
  //       console.error('Error fetching movies by country:', error);
  //     }
  //   });
  // }

  searchMovies(){
    this.currentPage = 1;
    this.itemsPerPage = 10;

    this.getAllMovies(this.keyword, this.genre_id, this.currentPage, this.itemsPerPage);
    console.log(this.getAllMovies(this.keyword, this.genre_id, this.currentPage, this.itemsPerPage));
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.getAllMovies(this.keyword, this.genre_id, this.currentPage, this.itemsPerPage);
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }

    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }

}
