import { Component, OnInit, AfterViewInit } from '@angular/core';
import { MovieService } from '../../services/movie.service';
import { FavouriteResponse } from '../../responses/user/favourite.response';

@Component({
  selector: 'app-movie-related',
  templateUrl: './movie-related.component.html',
  styleUrls: ['./movie-related.component.scss']
})
export class MovieRelatedComponent implements OnInit, AfterViewInit {

  movies: FavouriteResponse[] = [];
  constructor(private movieService: MovieService) {}

  ngOnInit(): void {
    this.movieService.currentData.subscribe((data) => {
      this.movies = data.movies;
    });
  }

  ngAfterViewInit(): void {
    this.initOwlCarousel();
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
}
