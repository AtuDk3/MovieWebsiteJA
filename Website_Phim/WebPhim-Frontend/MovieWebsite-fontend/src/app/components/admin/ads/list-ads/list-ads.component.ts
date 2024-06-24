import { Component } from '@angular/core';
import { AdsService } from '../../../../services/ads.service';
import { Router } from '@angular/router';
import { Ads } from '../../../../models/ads';
import { environment } from '../../../../environments/environment';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-list-ads',
  templateUrl: './list-ads.component.html',
  styleUrls: ['./list-ads.component.scss']
})
export class ListAdsComponent {

  ads: Ads[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 10;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];

  constructor(private adsService: AdsService,
              private router: Router,
              private sanitizer: DomSanitizer) {}

  ngOnInit() {  
    this.getAds(this.currentPage, this.itemsPerPage);
  }

  getAds(page: number, limit: number) {
    this.adsService.getAllAdsAdmin(page, limit).subscribe({
      next: (response: any) => {
        response.adsList.forEach((ads: Ads) => {
          // Kiểm tra và khởi tạo list_img_url nếu chưa có 
          this.loadImage(ads);
        });
        this.ads = response.adsList;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
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

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 0); // Bắt đầu từ 0
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages - 1); // Dựa vào totalPages

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 0); // Bắt đầu từ 0
    }

    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }

  goToPage(page: number, event: Event) {
    event.preventDefault();
    if (page >= 0 && page <= this.totalPages - 1) { // Đảm bảo không vượt quá giới hạn trang
      this.currentPage = page;
      this.getAds(this.currentPage, this.itemsPerPage);
    }
  }

  updateAds(ads_id: number) {
    this.router.navigate(['admin/ads/update-ads', ads_id]);
  }

  deleteAds(ads_id: number){
    this.adsService.deleteAds(ads_id).subscribe({
      next: () => {  
        this.getAds(this.currentPage,this.totalPages);      
      },
      error: (error: any) => {
        console.log(error);
      }

    });
  }
   
}
