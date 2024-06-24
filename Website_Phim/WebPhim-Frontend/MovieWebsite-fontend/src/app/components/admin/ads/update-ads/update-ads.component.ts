import { Component, OnInit } from '@angular/core';
import { Ads } from '../../../../models/ads';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AdsService } from '../../../../services/ads.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../../../../environments/environment';
@Component({
  selector: 'app-update-ads',
  templateUrl: './update-ads.component.html',
  styleUrl: './update-ads.component.scss'
})
export class UpdateAdsComponent implements OnInit {

  ads: Ads | null = null;
  selectedFiles: File[] = [];
  imageUrls: SafeUrl[] = [];
  ads_id: number = 0;


  constructor(
    private adsService: AdsService,
    private router: Router,
    private toastr: ToastrService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.ads_id = Number(id);
      this.adsService.getAdsById(id).subscribe({
        next: (response: Ads) => {
          debugger
          this.ads = response;
          this.loadImage(this.ads);
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

  loadImage(ads: Ads) {
    if (!Array.isArray(ads.list_img_url)) {
      ads.list_img_url = [];
    }
    ads.list_img.forEach(img => {
      this.adsService.getImage(img).subscribe({
        next: (imageBlob: Blob) => {
          const objectURL = URL.createObjectURL(imageBlob);
          this.imageUrls.push(this.sanitizer.bypassSecurityTrustUrl(objectURL));
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

  onFileSelected(event: any): void {
    const files: FileList = event.target.files;
    if (files && files.length > 0) {
      this.selectedFiles = Array.from(files);
      this.imageUrls = [];
      Array.from(files).forEach(file => {
        const reader = new FileReader();
        reader.onload = e => this.imageUrls.push(reader.result as string);
        reader.readAsDataURL(file);
      });
    }
  }
  updateAds() {
    if (this.ads_id && this.ads) {
      debugger
      if (this.selectedFiles.length>0) {
        this.adsService.uploadImagesFromCreateAds(this.selectedFiles).subscribe({
          next: (response: any) => {
            if (this.ads) {
              this.ads.list_img = response.filenames;// Use the filename from the response 
              this.adsService.updateAds(this.ads_id, this.ads).subscribe({
                next: () => {  
                  this.router.navigate(['admin/ads/list-ads']); 
                },
                error: (error: any) => {
                  console.log(error);
                }       
              });
            }          
          },
          error: (error: any) => {
            console.error('Failed to upload image:', error);
            this.toastr.error('There was a problem uploading the image.', 'Upload Failed', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
          }
        });
      }else{       
          this.adsService.updateAds(this.ads_id, this.ads!).subscribe({
            next: () => {  
              this.router.navigate(['admin/ads/list-ads']); 
            },
            error: (error: any) => {
              console.log(error);
            }       
          });             
      }



    }

  }
}
