import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, Output, EventEmitter, SimpleChanges, OnChanges } from '@angular/core';
import { Ads } from '../../models/ads';
import { AdsService } from '../../services/ads.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-video-ads',
  templateUrl: './video-ads.component.html',
  styleUrl: './video-ads.component.scss'
})
export class VideoAdsComponent  implements OnInit, AfterViewInit {
  @ViewChild('adVideo') adVideo!: ElementRef<HTMLVideoElement>;
  @Output() adSkipped = new EventEmitter<void>(); // Event to notify parent
  countdown: number = 10;
  adVisible: boolean = true;
  private countdownTimer: any;
  videoList: string[] = [];
  selectedVideoUrl: string = '';

  constructor(
    private adsService: AdsService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.getVideoAds();
  }

  ngAfterViewInit() {
    this.startCountdown();
    this.playSelectedVideo();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedVideoUrl'] && !changes['selectedVideoUrl'].isFirstChange()) {
      this.playSelectedVideo();
    }
  }

  getVideoAds() {
    this.adsService.getAllAds(0, 20).subscribe({
      next: (response: any) => {
        response.adsList.forEach((ads: Ads) => {
          if (ads.is_confirm === 1 && ads.is_active === 1 && ads.video) {
            this.videoList.push(ads.video);
          }
        });
        if (this.videoList.length > 0) {
          this.selectRandomVideo();
        } else {
          console.error('No videos available for ads.');
        }
      },
      error: (error: any) => {
        console.error('Error fetching ads!', error);
      }
    });
  }

  selectRandomVideo() {
    const randomIndex = Math.floor(Math.random() * this.videoList.length);
    this.selectedVideoUrl = this.videoList[randomIndex];
    console.log('Selected Video URL:', this.selectedVideoUrl);
    this.playSelectedVideo();
  }

  getVideoUrl(videoFilename: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(`assets/videos/${videoFilename}`);
  }

  startCountdown() {
    if (!this.countdownTimer) {
      this.countdownTimer = setInterval(() => {
        this.countdown--;
        if (this.countdown <= 0) {
          clearInterval(this.countdownTimer);
          this.countdownTimer = null;
        }
      }, 1000);
    }
  }

  onVideoPlay() {
    if (!this.countdownTimer) {
      this.startCountdown();
    }
  }

  onVideoPause() {
    if (this.countdownTimer) {
      clearInterval(this.countdownTimer);
      this.countdownTimer = null;
    }
  }

  skipAd() {
    this.adVisible = false;
    if (this.adVideo.nativeElement) {
      this.adVideo.nativeElement.pause();
      this.adVideo.nativeElement.currentTime = this.adVideo.nativeElement.duration; // Skip to end of the ad
    }
    this.adSkipped.emit(); // Notify parent component
  }

  playSelectedVideo() {
    if (this.adVideo && this.adVideo.nativeElement) {
      this.adVideo.nativeElement.load();
      this.adVideo.nativeElement.play();
    }
  }
}
