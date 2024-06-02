import { Component, OnInit } from '@angular/core';
import { EpisodeService } from '../../services/episode.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Episode } from '../../models/episode';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-watching',
  templateUrl: './watching.component.html',
  styleUrls: ['./watching.component.scss']
})
export class WatchingComponent implements OnInit {
  episodes: Episode[] = [];
  movieId: number = 0;
  safeUrl?: SafeResourceUrl;
  selectedEpisode?: Episode;

  constructor(
    private episodeService: EpisodeService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.movieId = +idParam;
    }

    if (!isNaN(this.movieId) && this.movieId > 0) {
      this.episodeService.getWatchingMovie(this.movieId).subscribe({
        next: (response: any) => {
          if (Array.isArray(response) && response.length > 0) {
            this.episodes = response;
            this.selectEpisode(this.episodes[0]);  // Select the first episode by default
          } else {
            console.log('Không có dữ liệu episode');
          }
        },
        error: (error: any) => {
          console.error('Lỗi khi tải dữ liệu episode:', error);
        }
      });
    } else {
      console.log('ID phim không hợp lệ');
    }
  }

  selectEpisode(episode: Episode) {
    this.selectedEpisode = episode;
    if (this.selectedEpisode && this.selectedEpisode.movieUrl) {
      this.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.selectedEpisode.movieUrl);
      console.log(this.selectedEpisode);
    } else {
      console.log('Dữ liệu episode không hợp lệ hoặc thiếu movieUrl');
    }
  }
}
