import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { Ads } from '../../../models/ads';
import { AdsService } from '../../../services/ads.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-ads-display',
  templateUrl: './ads-display.component.html',
  styleUrl: './ads-display.component.scss'
})
export class AdsDisplayComponent implements AfterViewInit{
  @ViewChild('dialog') dialog!: ElementRef<HTMLDialogElement>;


  ads: Ads[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 3;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];

  constructor(private adsService: AdsService,
              private sanitizer: DomSanitizer) {}

  ngOnInit() {  
    this.getAds(this.currentPage, this.itemsPerPage);
  }

  getAds(page: number, limit: number) {
    this.adsService.getAllAds(page, limit).subscribe({
      next: (response: any) => {
        response.adsList.forEach((ads: Ads) => {
          // Kiểm tra và khởi tạo list_img_url nếu chưa có 
          this.loadImage(ads);
        });
        this.ads = response.adsList              
      },
      error: (error: any) => {
        console.error('Error fetching ads!', error);
      }
    });
  }

  loadImage(ads: Ads) {
    if (!Array.isArray(ads.list_img_url)) {
      ads.list_img_url = [];
    }
    ads.list_img.forEach(img => {
      this.adsService.getImage(img).subscribe({
        next: (imageBlob: Blob) => {
          const objectURL = URL.createObjectURL(imageBlob);
          ads.list_img_url.push(this.sanitizer.bypassSecurityTrustUrl(objectURL));
        },
        error: (error) => {
          if (error.status === 403) {
            console.error('Access denied to image URL:', error.url);
          } else {
            console.error('Error loading image', error);
          }
        },
        complete: () => {
          console.log('Image loading complete for ads');
        }
      });
    });
  }

  closeModal() {
    this.dialog.nativeElement.close();
    this.dialog.nativeElement.classList.remove('opened');
  }

  openModal() {
    this.dialog.nativeElement.showModal();
    this.dialog.nativeElement.classList.add('opened');
  }

  ngAfterViewInit() {
    // Sử dụng sự kiện 'mousedown' thay vì 'click' để đảm bảo rằng sự kiện được xử lý đúng
    this.dialog.nativeElement.addEventListener('mousedown', (event: MouseEvent) => {
      const target = event.target as Element;
      if (target.nodeName === 'DIALOG') {
        this.closeModal();
      }
    });
    this.openModal();
  }

  
}
