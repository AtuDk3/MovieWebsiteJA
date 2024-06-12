import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Genre } from '../../../../models/genre';
import { GenreService } from '../../../../services/genre.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-genre',
  templateUrl: './add-genre.component.html',
  styleUrls: ['./add-genre.component.scss']
})
export class AddGenreComponent {
  genre: Genre = {
    id: 0,
    name: '',
    description: '',
    slug: '',
    is_active: 1
  };

  constructor(private genreService: GenreService, 
    private router: Router, 
    private toastr: ToastrService) {}

  addGenre() {

    this.genreService.createGenre(this.genre).subscribe({
      next: (response: any) => {
        this.toastr.success('The genre was added successfully!', 'Add Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
        this.router.navigate(['admin/genre/list-genre']);
      },
      error: (error: any) => {
        console.error('Failed to add genre:', error);
        this.toastr.error('There was a problem adding the genre.', 'Add Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  }
}
