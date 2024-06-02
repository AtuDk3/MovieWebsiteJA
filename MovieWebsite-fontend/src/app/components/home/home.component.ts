import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 18;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 
  keyword: string = '';
  genre_id: number = 0;
  movie_type_id: number = 0;

  constructor(private movieService: MovieService, private  router: Router, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.keyword = params['search'] || '';
      this.getAllMovies(this.keyword, this.genre_id, this.currentPage, this.itemsPerPage);
      //this.getAllMoviesByMovieType(this.movie_type_id, this.itemsPerPage);
    });
  }

  ngOnInit() {
    this.getAllMovies(this.keyword, this.genre_id, this.currentPage, this.itemsPerPage);
    //this.getAllMoviesByMovieType(this.movie_type_id, this.itemsPerPage);
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
        console.log(this.movies);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getAllMoviesByMovieType(movie_type_id:number, limit: number) {
    this.movieService.getAllMoviesByMovieType(movie_type_id, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });

        this.movies = response.movies;
        console.log(response)
      },
      error: (error: any) => {
        console.error('Error fetching movies by movie type:', error);
      }
    });
  }


}
