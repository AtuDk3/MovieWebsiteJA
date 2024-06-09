import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-top-views',
  templateUrl: './top-views.component.html',
  styleUrl: './top-views.component.scss'
})
export class TopViewsComponent implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 18;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 

  constructor(private movieService: MovieService,
              private  router: Router,
              private route: ActivatedRoute) {
    
  }

  ngOnInit() {
    this.getMoviesByNumberViews();
  }

  getMoviesByNumberViews() {
    this.movieService.getMoviesByNumberViews().subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });

        this.movies = response.movies;
        console.log(response)
        this.totalPages = response.totalPages;
      },
      error: (error: any) => {
        console.error('Error fetching movies by movie type:', error);
      }
    });
  }
}
