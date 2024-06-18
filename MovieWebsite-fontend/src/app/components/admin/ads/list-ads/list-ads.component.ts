import { Component, OnInit} from '@angular/core';
import { AdsService } from '../../../../services/ads.service';
import { Ads } from '../../../../models/ads';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-list-ads',
  templateUrl: './list-ads.component.html',
  styleUrl: './list-ads.component.scss'
})
export class ListAdsComponent implements OnInit {
  adsList: Ads[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 20;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = []; 
  keyword: string = '';
  message: string | null = null;

  constructor(private adsService: AdsService,
    private  router: Router,
    private toastr: ToastrService,
    private route: ActivatedRoute) {
  //  const navigation = this.router.getCurrentNavigation();
  //  if (navigation && navigation.extras.state && navigation.extras.state['message']) {
  //    this.message = navigation.extras.state['message'];
  //  }
 }

 ngOnInit() {
  this.route.params.subscribe(params => {
    this.getAllAds( this.currentPage, this.itemsPerPage);
  });
 }

 getAllAds( page: number, limit: number) {
  this.adsService.getAllAds(page, limit).subscribe({
    
    next: (response: any) => {
      response.adsList.forEach((ads: Ads) => {
        ads.url = `${environment.apiBaseUrl}/ads/images/${ads.banner_ads}`;

        ads.create_at_formated = this.formatDate(ads.create_at);
        ads.expiration_at_formated = this.formatDate(ads.expiration_at);
      });

      this.adsList = response.adsList;
      this.totalPages = response.totalPages;
      this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
    },
    error: (error: any) => {
      console.error('Error fetching ads', error);
    }
  });
}

formatDate(d: Date): string {
  const date = new Date(d);
  const day = date.getDate();
  const month = date.getMonth() + 1; // getMonth() is zero-based
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
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
    this.getAllAds(this.currentPage, this.itemsPerPage);
  }
}

 updateAds(ads: Ads) {
   this.router.navigate(['admin/ads/update-ads', ads.id]);
 }

 deleteAds(id: number) {
   Swal.fire({
     title: 'Are you sure?',
     text: 'You will not be able to recover this ads!',
     icon: 'warning',
     showCancelButton: true,
     confirmButtonColor: '#3085d6',
     cancelButtonColor: '#d33',
     confirmButtonText: 'Yes, delete it!',
   }).then((result) => {
     if (result.isConfirmed) {
       this.adsService.deleteAds(id).subscribe({
         next: () => {
           this.toastr.success('Ads deleted successfully!', 'Delete Success', {
             timeOut: 3000,
             positionClass: 'toast-bottom-right'
           });
           this.getAllAds(this.currentPage, this.itemsPerPage);
         },
         error: (error: any) => {
           console.log(error);
           this.toastr.error('There was a problem deleting the ads.', 'Delete Failed', {
             timeOut: 3000,
             positionClass: 'toast-bottom-right'
           });
         }
       });
     }
   });
 }
}
