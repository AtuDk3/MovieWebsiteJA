import { Component } from '@angular/core';
import { MovieService } from '../../../services/movie.service';
import { environment } from '../../../environments/environment';
import { Movie } from '../../../models/movie';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-get-movie-api',
  templateUrl: './get-movie-api.component.html',
  styleUrl: './get-movie-api.component.scss'
})
export class GetMovieApiComponent {

  movies: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 

  constructor(
    private movieService: MovieService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {  
      this.getMovies( this.currentPage, this.itemsPerPage);   
   }

   getMovies( page: number, limit: number) {
    this.movieService.getMovieAPI(page, limit).subscribe({
      next: (response: any) => {
        response.movies.forEach((movie: Movie) => {
          if(!movie.image.includes('http')){
            movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
          }else{
            movie.url = movie.image;
          }                    
        });
  
        this.movies = response.movies;
        console.log(this.movies)
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      error: (error: any) => {
        console.error('Error fetching movies!', error);
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
      this.getMovies(this.currentPage, this.itemsPerPage);
    }
  }

  createMovieAPI(){
    this.movieService.createMovieAPI().subscribe({
      next: (response: any) => { 
        this.getMovies(this.currentPage, this.itemsPerPage);
      },
      error: (error: any) => {
        console.error('Error create movies from api!', error);
      }
    });
  }

  deleteMovie(id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this movie!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.movieService.deleteMovie(id).subscribe({
          next: () => {
            this.toastr.success('Movie deleted successfully!', 'Delete Success', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
            this.getMovies(this.currentPage, this.itemsPerPage);
          },
          error: (error: any) => {
            console.log(error);
            this.toastr.error('There was a problem deleting the movie.', 'Delete Failed', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
          }
        });
      }
    });
  }


}