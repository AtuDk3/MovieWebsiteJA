import { Component, OnInit } from '@angular/core';
import { AdsDTO } from '../../../../dtos/user/ads.dto';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AdsService } from '../../../../services/ads.service';
import { Ads } from '../../../../models/ads';

@Component({
  selector: 'app-add-ads',
  templateUrl: './add-ads.component.html',
  styleUrl: './add-ads.component.scss'
})
export class AddAdsComponent implements OnInit {

  ads: AdsDTO = {
    email: '',
    name: '',
    description: '',
    number_days: 0,
    amount: 0,
    list_img: []
  }

  imageUrls: SafeUrl[] = [];
  selectedFiles: File[] = [];
  adsResponse: Ads | null = null;

  constructor(
    private adsService: AdsService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {

  }

  addAds() {
    if (this.selectedFiles.length > 0) {
      this.uploadImageAndCreateAds();
    } else {
      this.createAds();
    }
  }

  uploadImageAndCreateAds() {
    if (this.selectedFiles) {
      this.adsService.uploadImagesFromCreateAds(this.selectedFiles).subscribe({
        next: (response: any) => {
          this.ads.list_img = response.filenames; // Use the filename from the response
          this.createAds();
        },
        error: (error: any) => {
          console.error('Failed to upload image:', error);
          this.toastr.error('There was a problem uploading the image.', 'Upload Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
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

  createAds() {
    this.adsService.createAds(this.ads).subscribe({
      next: (response: any) => {
        this.toastr.success('The ads was added successfully!', 'Add Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
        this.adsResponse = response
        if (this.adsResponse) {
          this.adsService.sendTradingCode(this.adsResponse.trading_code, this.adsResponse.email).subscribe({
            next: (response: any) => {             
            },
            error: (error: any) => {
            }
          });
          this.router.navigate(['admin/ads/list-ads']);
        }
      },
      error: (error: any) => {
        console.error('Failed to add movie:', error);
        this.toastr.error('There was a problem adding the ads.', 'Add Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  }


}