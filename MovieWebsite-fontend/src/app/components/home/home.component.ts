import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environments';
import { MovieService } from '../../service/movie.service';
import { Movie } from '../../models/movie';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 

  constructor(private movieService: MovieService) {
    ngOnInit(){
      this.getMovies(this.currentPage, this.itemsPerPage);
    }

    getMovies(page: number, limit: number){
      this.moviesService.getMovies(page, limit).subscribe({
        next: (response: any) => {
          response.movies.forEach((movie: Movie) => {
            movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
          });
          this.movies = response.movies;
          this.totalPages = response.totalPages;
          this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
        },
        complete: () => {

        },

        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

  onPageChange(page: number){
    this.currentPage = page;
    this.getMovies(this.currentPage, this.itemsPerPage);
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[]{
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 1);
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages);

    if (endPage - startPage + 1 < maxVisiblePages){
      startPage = Math.max(endPage - maxVisiblePages + 1, 1);
    }
    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }
}
