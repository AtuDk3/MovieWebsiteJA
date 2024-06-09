import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MovieType } from '../../../../models/movie_type';
import { MovieTypeService } from '../../../../services/movie_type.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-movie-type',
  templateUrl: './add-movie-type.component.html',
  styleUrl: './add-movie-type.component.scss'
})
export class AddMovieTypeComponent {
  movieType: MovieType = {
    id: 0,
    name: '',
    isActive: 1
  };

  constructor(private movieTypeService: MovieTypeService, 
    private router: Router, 
    private toastr: ToastrService) {}

  addMovieType() {
    this.movieTypeService.createMovieType(this.movieType).subscribe({
      next: (response: any) => {
        this.toastr.success('The movie type was added successfully!', 'Add Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
        this.router.navigate(['admin/movie-type/list-movie-type']);
      },
      error: (error: any) => {
        console.error('Failed to add movie type:', error);
        this.toastr.error('There was a problem adding the movie type.', 'Add Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  }
}
