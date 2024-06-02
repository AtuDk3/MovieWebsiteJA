import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
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

