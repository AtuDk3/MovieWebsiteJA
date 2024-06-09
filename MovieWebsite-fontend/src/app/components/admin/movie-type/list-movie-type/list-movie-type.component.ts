import { Component, OnInit} from '@angular/core';
import { MovieTypeService } from '../../../../services/movie_type.service';
import { MovieType } from '../../../../models/movie_type';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-movie-type',
  templateUrl: './list-movie-type.component.html',
  styleUrl: './list-movie-type.component.scss'
})
export class ListMovieTypeComponent implements OnInit {
  movieTypes: MovieType[] = [];
  message: string | null = null;

  constructor(private movieTypeService: MovieTypeService,
    private  router: Router,
    private toastr: ToastrService) {
   const navigation = this.router.getCurrentNavigation();
   if (navigation && navigation.extras.state && navigation.extras.state['message']) {
     this.message = navigation.extras.state['message'];
   }
 }

 ngOnInit() {
   this.getListMovieTypes();
 }

 getListMovieTypes() {
   this.movieTypeService.getMovieTypes().subscribe({
     next: (response: any) => {
       this.movieTypes = response;
       console.log(this.movieTypes);
     },
     error: (error: any) => {
       console.log(error);
     }
   });
 }

 updateMovieType(movieType: MovieType) {
   this.router.navigate(['admin/movie-type/update-movie-type', movieType.id]);
 }

 deleteMovieType(id: number) {
   Swal.fire({
     title: 'Are you sure?',
     text: 'You will not be able to recover this movie type!',
     icon: 'warning',
     showCancelButton: true,
     confirmButtonColor: '#3085d6',
     cancelButtonColor: '#d33',
     confirmButtonText: 'Yes, delete it!',
   }).then((result) => {
     if (result.isConfirmed) {
       this.movieTypeService.deleteMovieType(id).subscribe({
         next: () => {
           this.toastr.success('Movie type deleted successfully!', 'Delete Success', {
             timeOut: 3000,
             positionClass: 'toast-bottom-right'
           });
           this.getListMovieTypes();
         },
         error: (error: any) => {
           console.log(error);
           this.toastr.error('There was a problem deleting the movie type.', 'Delete Failed', {
             timeOut: 3000,
             positionClass: 'toast-bottom-right'
           });
         }
       });
     }
   });
 }
}
