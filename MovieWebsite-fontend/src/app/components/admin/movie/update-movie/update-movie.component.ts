import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from '../../../../services/movie.service';
import { Movie } from '../../../../models/movie';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../../../environments/environment';
import { Genre } from '../../../../models/genre';
import { Country } from '../../../../models/country';
import { MovieType } from '../../../../models/movie_type';
import { GenreService } from '../../../../services/genre.service';
import { CountryService } from '../../../../services/country.service';
import { MovieTypeService } from '../../../../services/movie_type.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-update-movie',
  templateUrl: './update-movie.component.html',
  styleUrl: './update-movie.component.scss'
})
export class UpdateMovieComponent implements OnInit {
  movie: Movie | null = null;
  genres: Genre[] = [];
  countries: Country[] = [];
  movieTypes: MovieType[] = [];
  selectedFile: File | null = null;
  imageUrl: SafeUrl | string | null = null;
  movieId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private movieService: MovieService,
    private genreService: GenreService,
    private countryService: CountryService,
    private movieTypeService: MovieTypeService,
    private router: Router,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer,
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.movieId = Number(id);
      this.movieService.getMovieById(id).subscribe({
        next: (response: Movie) => {   
          this.movie = response;
          this.imageUrl = `${environment.apiBaseUrl}/movies/images/${this.movie.image}`;
          
          this.movie.release_date = new Date(response.release_date);
          const releaseDate = new Date(this.movie.release_date);
          const day = ('0' + releaseDate.getDate()).slice(-2); // Thêm số 0 nếu cần
          const month = ('0' + (releaseDate.getMonth() + 1)).slice(-2); // Thêm số 0 nếu cần
          const year = releaseDate.getFullYear();
          const formattedDate = `${year}-${month}-${day}`; // Định dạng thành yyyy-mm-dd
          this.movie.release_date_formated = formattedDate;
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
    this.getGenres();
    this.getCountries();
    this.getMovieTypes();
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

  updateMovie() {
    if (this.movie) {
      this.movie.release_date = new Date(this.movie.release_date_formated);

      this.movieService.updateMovie(this.movie).subscribe({

        next: (response: any) => {

          console.log(this.movie?.image)
          this.toastr.success('The movie was updated successfully!', 'Update Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate(['/admin/movie/list-movie']);
        },
        error: (error: any) => {
          console.log(error);
          this.toastr.error('There was a problem updating the movie.', 'Update Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }

  loadImage(imageName: string) {
    this.movieService.getImage(imageName).subscribe({
      next: (imageBlob: Blob) => {
        const objectURL = URL.createObjectURL(imageBlob);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      error: (error) => {
        console.error('Error loading image', error);
      },
      complete: () => {
        console.log('Image loading complete');
      }
    });
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    debugger
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = e => this.imageUrl = reader.result;
      reader.readAsDataURL(file);
      console.log(this.movieId)
      this.movieService.uploadImageMovie(this.movieId, file).subscribe({
        next: (response) => {
          console.log('File uploaded successfully', response);
          // Handle response here if needed
        },
        error: (error) => {
          console.error('Error uploading file', error);
          // Handle error here
        }
      });
    }
  }


}