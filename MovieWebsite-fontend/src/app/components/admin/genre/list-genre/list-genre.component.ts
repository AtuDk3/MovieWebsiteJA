import { Component, OnInit} from '@angular/core';
import { GenreService } from '../../../../services/genre.service';
import { Genre } from '../../../../models/genre';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-genre',
  templateUrl: './list-genre.component.html',
  styleUrl: './list-genre.component.scss'
})
export class ListGenreComponent implements OnInit {
  genres: Genre[] = [];
  message: string | null = null;

  constructor(private genreService: GenreService,
     private  router: Router,
     private toastr: ToastrService) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation && navigation.extras.state && navigation.extras.state['message']) {
      this.message = navigation.extras.state['message'];
    }
  }

  ngOnInit() {
    this.getListGenres();
  }

  getListGenres() {
    this.genreService.getGenres().subscribe({
      next: (response: any) => {
        this.genres = response;
        console.log(this.genres);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  updateGenre(genre: Genre) {
    this.router.navigate(['admin/genre/update-genre', genre.id]);
  }

  deleteGenre(id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this genre!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.genreService.deleteGenre(id).subscribe({
          next: () => {
            this.toastr.success('Genre deleted successfully!', 'Delete Success', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
            this.getListGenres();
          },
          error: (error: any) => {
            console.log(error);
            this.toastr.error('There was a problem deleting the genre.', 'Delete Failed', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
          }
        });
      }
    });
  }
}