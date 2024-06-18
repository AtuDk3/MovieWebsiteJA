import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';
import { AdsComponent } from '../ads/ads.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  @ViewChild(AdsComponent) adPopup!: AdsComponent;
  theatersMovie: Movie[] = [];
  singleMovie: Movie[] = [];
  seriesMovie: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 18;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 
  keyword: string = '';

  constructor(private movieService: MovieService, private  router: Router, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.keyword = params['search'] || '';
    });
  }

  ngOnInit() {
    this.loadMovies();
  }

  loadMovies() {
    this.getTheatersMovie();
    this.getSingleMovie();
    this.getSeriesMovie();
  }

  getTheatersMovie() {
    this.getMoviesByMovieTypeId(3, 0, 18).subscribe({
      next: (response: any) => {
        this.theatersMovie = response.movies.map((movie: Movie) => ({
          ...movie,
          url: `${environment.apiBaseUrl}/movies/images/${movie.image}`
        }));
        console.log('Movies Chieu Rap:', response);
      },
      error: (error: any) => {
        console.error('Error fetching movies Chieu Rap:', error);
      }
    });
  }

  getSingleMovie() {
    this.getMoviesByMovieTypeId(2, 0, 18).subscribe({
      next: (response: any) => {
        this.singleMovie = response.movies.map((movie: Movie) => ({
          ...movie,
          url: `${environment.apiBaseUrl}/movies/images/${movie.image}`
        }));
        console.log('Movies Le:', response);
      },
      error: (error: any) => {
        console.error('Error fetching movies Le:', error);
      }
    });
  }

  getSeriesMovie() {
    this.getMoviesByMovieTypeId(1, 0, 18).subscribe({
      next: (response: any) => {
        this.seriesMovie = response.movies.map((movie: Movie) => ({
          ...movie,
          url: `${environment.apiBaseUrl}/movies/images/${movie.image}`
        }));
        console.log('Movies Bo:', response);
      },
      error: (error: any) => {
        console.error('Error fetching movies Bo:', error);
      }
    });
  }

  getMoviesByMovieTypeId(movie_type_id: number, page: number, limit: number) {
    return this.movieService.getMoviesByMovieTypeId(movie_type_id, page, limit);
  }
}