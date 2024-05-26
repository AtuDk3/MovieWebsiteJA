import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environments';
import { MovieService } from '../../service/movie.service';
import { GenreService } from '../../service/genre.service';
import { Movie } from '../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';

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
    private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    //const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    const idParam = 20;
    if(idParam !== null){
      this.movieId = +idParam;
    }
    if(!isNaN(this.movieId)){
      this.movieService.getDetailMovie(this.movieId).subscribe({
        next: (response: any) => {
          this.movie = response;
          if(this.movie && this.movie.image){
          this.movie.url = `${environment.apiBaseUrl}/movies/images/${this.movie.image}`;
          }
        },
        error: (error: any) => {
          console.log(error);
        }
      })
    }
  }

  

  

}

