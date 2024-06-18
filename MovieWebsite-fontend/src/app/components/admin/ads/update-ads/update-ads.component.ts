import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdsService } from '../../../../services/ads.service';
import { Ads } from '../../../../models/ads';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../../../environments/environment';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-update-ads',
  templateUrl: './update-ads.component.html',
  styleUrl: './update-ads.component.scss'
})
export class UpdateAdsComponent implements OnInit {
  ads: Ads | null = null;
  selectedFile: File | null = null;
  imageUrl: SafeUrl | string | null = null;
  adsId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private adsService: AdsService,
    private router: Router,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer,
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.adsId = Number(id);
      this.adsService.getAdsById(id).subscribe({
        next: (response: Ads) => {   
          this.ads = response;
          this.imageUrl = `${environment.apiBaseUrl}/ads/images/${this.ads.banner_ads}`;
        
          this.ads.create_at_formated = this.formatDate(this.ads.create_at);
          this.ads.expiration_at_formated = this.formatDate(this.ads.expiration_at);
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

  formatDate(d: Date): string {
    const date = new Date(d);
    const day = date.getDate();
    const month = date.getMonth() + 1; // getMonth() is zero-based
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  }

  updateAds() {
    if (this.ads) {
      this.ads.create_at_formated = this.formatDate(this.ads.create_at);
      this.ads.expiration_at_formated = this.formatDate(this.ads.expiration_at);

      this.adsService.updateAds(this.ads).subscribe({

        next: (response: any) => {

          console.log(this.ads?.banner_ads)
          this.toastr.success('The ads was updated successfully!', 'Update Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate(['/admin/ads/list-ads']);
        },
        error: (error: any) => {
          console.log(error);
          this.toastr.error('There was a problem updating the ads.', 'Update Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }

  loadImage(imageName: string) {
    this.adsService.getImage(imageName).subscribe({
      next: (imageBlob: Blob) => {
        const objectURL = URL.createObjectURL(imageBlob);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      error: (error) => {
        console.error('Error loading image', error);
      },
      complete: () => {
        console.log('Image loading complete');
      }
    });
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    debugger
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = e => this.imageUrl = reader.result;
      reader.readAsDataURL(file);
      console.log(this.adsId)
      this.adsService.uploadImageAds(this.adsId, file).subscribe({
        next: (response) => {
          console.log('File uploaded successfully', response);
          // Handle response here if needed
        },
        error: (error) => {
          console.error('Error uploading file', error);
          // Handle error here
        }
      });
    }
  }
}
