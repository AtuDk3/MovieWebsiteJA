import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-search-movie',
  templateUrl: './search-movie.component.html',
  styleUrl: './search-movie.component.scss'
})
export class SearchMovieComponent {
  movies: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 18; 
  keyword: string = '';

  constructor(private movieService: MovieService, private  router: Router, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.keyword = params['search'] || '';
      this.getAllMovies(this.keyword, this.currentPage, this.itemsPerPage);
    });
  }

  getAllMovies(keyword: string, page: number, limit: number) {
    this.movieService.getSearchMovies(keyword, page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });   
        this.movies = response.movies;        
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
}