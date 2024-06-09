import { Component, OnInit} from '@angular/core';
import { MovieService } from '../../../../services/movie.service';
import { Movie } from '../../../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-list-movie',
  templateUrl: './list-movie.component.html',
  styleUrl: './list-movie.component.scss'
})
export class ListMovieComponent  implements OnInit {
  movies: Movie[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 20;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 
  keyword: string = '';
  message: string | null = null;
  genre_id: number = 0;

  constructor(private movieService: MovieService,
    private  router: Router,
    private toastr: ToastrService,
    private route: ActivatedRoute) {
   const navigation = this.router.getCurrentNavigation();
   if (navigation && navigation.extras.state && navigation.extras.state['message']) {
     this.message = navigation.extras.state['message'];
   }
 }

 ngOnInit() {
  this.route.params.subscribe(params => {
    this.getMovies( this.currentPage, this.itemsPerPage);
  });
 }

 getMovies( page: number, limit: number) {
  this.movieService.getMovies(page, limit).subscribe({
    next: (response: any) => {
      response.movies.forEach((movie: Movie) => {
        movie.url = `${environment.apiBaseUrl}/movies/images/${movie.image}`;
        release_date: new Date(response.release_date);

        const releaseDate = new Date(movie.release_date);
            const day = releaseDate.getDate();
            const month = releaseDate.getMonth() + 1;
            const year = releaseDate.getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            movie.release_date_formated = formattedDate;

            console.log(response);
      });

      this.movies = response.movies;
      this.totalPages = response.totalPages;
      this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
    },
    error: (error: any) => {
      console.error('Error fetching movies by genre:', error);
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

 updateMovie(movie: Movie) {
   this.router.navigate(['admin/movie/update-movie', movie.id]);
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
