import { Component, OnInit } from '@angular/core';
import { EpisodeService } from '../../../../services/episode.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EpisodeResponse } from '../../../../responses/user/episode.response';
import Swal from 'sweetalert2';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-list-episode-by-movie',
  templateUrl: './list-episode-by-movie.component.html',
  styleUrl: './list-episode-by-movie.component.scss'
})
export class ListEpisodeByMovieComponent implements OnInit{

  episodeResponse: EpisodeResponse[]= [];
  movieId: number=0;

  constructor(private episodeService: EpisodeService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ){}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.movieId= id;
      this.listEpisodeByMovie(id);
    }
  }

  listEpisodeByMovie(movie_id: number) {
    this.episodeService.getEpisodeByMovie(movie_id).subscribe({
      next: (response: any) => {
        console.log('API Response:', response); // Log the response to see its actual structure
  
        if (Array.isArray(response)) {
          response.forEach((episode: EpisodeResponse) => {
            const day_create = ('0' + (new Date(episode.created_at).getDate())).slice(-2);
            const month_create = ('0' + (new Date(episode.created_at).getMonth() + 1)).slice(-2);
            const year_create = new Date(episode.created_at).getFullYear();
            episode.created_at_format = `${day_create}/${month_create}/${year_create}`;
  
            const day_update = ('0' + (new Date(episode.updated_at).getDate())).slice(-2);
            const month_update = ('0' + (new Date(episode.updated_at).getMonth() + 1)).slice(-2);
            const year_update = new Date(episode.updated_at).getFullYear();
            episode.updated_at_format = `${day_update}/${month_update}/${year_update}`;
          });
          this.episodeResponse = response;
        } else {
          console.error('Expected an array but received:', response);
          // Handle the unexpected response structure here if necessary
        }
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  updateEpisode(episode_id: number){
    this.router.navigate([`admin/episode/update-episode/${episode_id}`]);
  }

  deleteEpisode(episode_id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this episode!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.episodeService.deleteEpisode(episode_id).subscribe({
          next: () => {
            this.toastr.success('Episode deleted successfully!', 'Delete Success', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
            this.listEpisodeByMovie(this.movieId);
          },
          error: (error: any) => {
            console.log(error);
            this.toastr.error('There was a problem deleting the Episode.', 'Delete Failed', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
          }
        });
      }
    });
  }
  
}
