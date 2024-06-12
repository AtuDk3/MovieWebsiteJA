import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieTypeService } from '../../../../services/movie_type.service';
import { MovieType } from '../../../../models/movie_type';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-movie-type',
  templateUrl: './update-movie-type.component.html',
  styleUrl: './update-movie-type.component.scss'
})
export class UpdateMovieTypeComponent implements OnInit {
  movieType: MovieType | null = null;

  constructor(
    private route: ActivatedRoute,
    private movieTypeService: MovieTypeService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.movieTypeService.getMovieTypeById(id).subscribe({
        next: (response: MovieType) => {
          this.movieType = response;
          
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

  updateMovieType() {
    if (this.movieType) {
      debugger
      this.movieTypeService.updateMovieType(this.movieType).subscribe({
        next: (response: any) => {
          this.toastr.success('The movie type was updated successfully!', 'Update Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate(['/admin/movie-type/list-movie-type']);
        },
        error: (error: any) => {
          console.log(error);
          this.toastr.error('There was a problem updating the movie type.', 'Update Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }
}
