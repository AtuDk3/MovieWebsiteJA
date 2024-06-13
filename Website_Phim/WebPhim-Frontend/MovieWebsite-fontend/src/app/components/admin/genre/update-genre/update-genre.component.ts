import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GenreService } from '../../../../services/genre.service';
import { Genre } from '../../../../models/genre';
import { ToastrService } from 'ngx-toastr';
import { GenreDTO } from '../../../../dtos/user/genre.dto';

@Component({
  selector: 'app-update-genre',
  templateUrl: './update-genre.component.html',
  styleUrls: ['./update-genre.component.scss']
})
export class UpdateGenreComponent implements OnInit {
  genreDTO: GenreDTO | null = null;

  constructor(
    private route: ActivatedRoute,
    private genreService: GenreService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.genreService.getGenreById(id).subscribe({
        next: (response: GenreDTO) => {
          this.genreDTO = response;         
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

  updateGenre() {
    
    if (this.genreDTO) {   
      this.genreService.updateGenre(this.genreDTO).subscribe({
        
        next: (response: any) => {       
          this.toastr.success('The genre was updated successfully!', 'Update Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate(['/admin/genre/list-genre']);
        },
        error: (error: any) => {
          console.log(error);
          this.toastr.error('There was a problem updating the genre.', 'Update Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }

  
}