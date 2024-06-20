import { Component, OnInit } from '@angular/core';
import { EpisodeService } from '../../../../services/episode.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EpisodeDTO } from '../../../../dtos/user/episode.dto';

@Component({
  selector: 'app-add-episode',
  templateUrl: './add-episode.component.html',
  styleUrl: './add-episode.component.scss'
})
export class AddEpisodeComponent implements OnInit{

  movie_id: number= 0;
  episode: number= 1;
  movie_url: string= '';

  constructor(private episodeService: EpisodeService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
  ){}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.movie_id= id;
    }
  }

  addEpisode(){
    const episodeDTO: EpisodeDTO = {
      movie_id: this.movie_id,
      episode: this.episode,
      movie_url: this.movie_url
    };
    this.episodeService.addEpisode(episodeDTO)
      .subscribe({
        next: (response: any) => {
          this.toastr.success('The episode was added successfully!', 'Add Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate([`admin/episode/list-episode-by-movie/${response.movie_id}`]);
        },
        error: (error: any) => {
          console.error('Failed to add episode:', error);
          this.toastr.error('Episode existing!', 'Add Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
  }
}
