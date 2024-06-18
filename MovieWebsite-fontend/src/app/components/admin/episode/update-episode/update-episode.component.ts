import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EpisodeService } from '../../../../services/episode.service';
import { EpisodeDTO } from '../../../../dtos/user/episode.dto';
import { ToastrService } from 'ngx-toastr';
import { Episode } from '../../../../models/episode';
@Component({
  selector: 'app-update-episode',
  templateUrl: './update-episode.component.html',
  styleUrl: './update-episode.component.scss'
})
export class UpdateEpisodeComponent {

  id: number=0;
  episode: Episode | null = null;

  constructor(private episodeService: EpisodeService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
  ){}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.id= id;
      this.getInfoEpisode(id);
    }
  }

  getInfoEpisode(episode_id: number){
    this.episodeService.getInfoEpisode(episode_id).subscribe({
      next: (response: Episode) => {
        this.episode = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
  

  updateEpisode(){
    if(this.episode){
    const episodeDTO: EpisodeDTO = {
      movie_id: 0,
      episode: this.episode.episode,
      movie_url: this.episode.movieUrl
    };
  
    this.episodeService.updateEpisode(this.id,episodeDTO)
      .subscribe({
        next: (response: any) => {
          this.toastr.success('The episode was updated successfully!', 'Update Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate([`admin/episode/list-episode-by-movie/${response.movie_id}`]);
       },
        error: (error: any) => {
          console.error('Error updating episode details', error);  
          this.toastr.error('Episode existing!', 'Update Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });                                                                                      
        },
      });
  }

  }
}