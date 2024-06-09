import { Component, OnInit, Renderer2, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { environment } from '../../environments/environment';
import { MovieService } from '../../services/movie.service';
import { GenreService } from '../../services/genre.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

declare var FB: any;

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.scss'
})
export class MovieDetailsComponent implements OnInit {
  movie?: Movie;
  movieId: number = 0; 

  constructor(
    private movieService: MovieService, 
    private genreService: GenreService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document) {}

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {

        if (typeof FB !== 'undefined') {
          FB.XFBML.parse();
        }
      }
    });

    const idParam = this.activatedRoute.snapshot.paramMap.get('id');

    if(idParam !== null){
      this.movieId = +idParam;
    }
    if(!isNaN(this.movieId)){
      this.movieService.getDetailMovie(this.movieId).subscribe({
        next: (response: any) => {
          // this.movie = response;
          this.movie = {
            ... response, 
            release_date: new Date(response.release_date)
          };   
          if(this.movie && this.movie.image){
          this.movie.url = `${environment.apiBaseUrl}/movies/images/${this.movie.image}`;
          } 
          if (this.movie && this.movie.release_date) {
            const releaseDate = new Date(this.movie.release_date);
            const day = releaseDate.getDate();
            const month = releaseDate.getMonth() + 1;
            const year = releaseDate.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            this.movie.release_date_formated = formattedDate;
        } else {
            console.log("Ngày phát hành không tồn tại.");
        }     
        },
        error: (error: any) => {
          console.log(error);
        }
      })
    }
  }
}

