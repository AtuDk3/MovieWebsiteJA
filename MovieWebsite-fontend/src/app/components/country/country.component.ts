import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { MovieService } from '../../service/movie.service';
import { Movie } from '../../models/movie';
import { Router, ActivatedRoute } from '@angular/router';
import { consumerPollProducersForChange } from '@angular/core/primitives/signals';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrl: './country.component.scss'
})
export class CountryComponent implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 0; 
  itemsPerPage: number = 6;
  totalPages: number = 0;
  visiblePages: number[] = []; 
  country_id: number = 0;
  countryName: string = '';

  constructor(
    private movieService: MovieService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.country_id = +params['country_id'];
      this.getMoviesByCountryId(this.country_id, this.currentPage, this.itemsPerPage);
    });
  }

  getMoviesByCountryId(country_id: number, page: number, limit: number) {
    this.movieService.getMoviesByCountryId(country_id, page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        });

        this.movies = response.movies;
        this.countryName = response.movies.length > 0 ? response.movies[0].country_name : '';
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
        console.log(this.movies);
      },
      error: (error: any) => {
        console.error('Error fetching movies by country:', error);
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
      this.getMoviesByCountryId(this.country_id, this.currentPage, this.itemsPerPage);
    }
  }
}
