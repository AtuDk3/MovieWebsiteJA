import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from '../../../../models/movie';
import { Genre } from '../../../../models/genre';
import { MovieService } from '../../../../services/movie.service';
import { ToastrService } from 'ngx-toastr';
import { GenreService } from '../../../../services/genre.service';
import { CountryService } from '../../../../services/country.service';
import { MovieTypeService } from '../../../../services/movie_type.service';
import { Country } from '../../../../models/country';
import { MovieType } from '../../../../models/movie_type';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.scss']
})
export class AddMovieComponent implements OnInit {
  movie: Movie = {
    id: 0,
    name: '',
    description: '',
    image: '',
    slug: '',
    release_date: new Date(),
    duration: '',
    id_genre: 0,
    id_movie_type: 0,
    id_country: 0,
    episode: 0,
    hot: 0,
    is_fee: 0,
    season: 1,
    limited_age: 0,
    number_views: 0,
    is_active: 1,
    movie_type_name: '',
    genre_name: '',
    country_name: '',
    url: '',
    release_date_formated: ''
  };

  genres: Genre[] = [];
  countries: Country[] = [];
  movieTypes: MovieType[] = [];
  imageUrl: SafeUrl | string | null = null;
  selectedFile: File | null = null;

  constructor(
    private movieService: MovieService, 
    private genreService: GenreService,
    private countryService: CountryService,
    private movieTypeService: MovieTypeService,
    private router: Router, 
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.getGenres();
    this.getCountries();
    this.getMovieTypes();
  }

  addMovie() {
    if (this.selectedFile) {
      this.uploadImageAndCreateMovie();
    } else {
      this.createMovie();
    }
  }

  uploadImageAndCreateMovie() {
    if (this.selectedFile) {
      this.movieService.uploadImageFromCreateMovie(this.selectedFile).subscribe({
        next: (response: any) => {
          this.movie.image = response.filename; // Use the filename from the response
          this.createMovie();
        },
        error: (error: any) => {
          console.error('Failed to upload image:', error);
          this.toastr.error('There was a problem uploading the image.', 'Upload Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }

  createMovie() {
    this.movieService.createMovie(this.movie).subscribe({
      next: (response: any) => {
        this.toastr.success('The movie was added successfully!', 'Add Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
        this.router.navigate(['admin/movie/list-movie']);
      },
      error: (error: any) => {
        console.error('Failed to add movie:', error);
        this.toastr.error('There was a problem adding the movie.', 'Add Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = e => this.imageUrl = reader.result;
      reader.readAsDataURL(file);
    }
  }

  getGenres() {
    this.genreService.getGenres().subscribe({
      next: (response: any) => {
        this.genres = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getCountries() {
    this.countryService.getCountries().subscribe({
      next: (response: any) => {
        this.countries = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getMovieTypes() {
    this.movieTypeService.getMovieTypes().subscribe({
      next: (response: any) => {
        this.movieTypes = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
}