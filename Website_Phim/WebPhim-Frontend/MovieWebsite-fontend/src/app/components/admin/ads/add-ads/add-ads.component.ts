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
    position: '',
    number_days: 0,
    amount: 0,
    list_img: [],
    video: ''
  }

  imageUrls: SafeUrl[] = [];
  selectedFiles: File[] = [];
  adsResponse: Ads | null = null;
  selectedVideo: File | null = null; 

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
    } else if (this.selectedVideo) { // Check if selectedVideo is not null
      this.uploadVideoAndCreateAds();
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

  onVideoSelected(event: any): void {
  const file: File = event.target.files[0];
  if (file && file.type.startsWith('video/')) {
    const reader = new FileReader();
    this.selectedVideo = file;
    reader.onload = e => this.ads.video = reader.result as string;
    reader.readAsDataURL(file);
  } else {
    // Handle invalid file type
    this.toastr.error('Please select a valid video file.', 'Invalid File', {
      timeOut: 3000,
      positionClass: 'toast-bottom-right'
    });
  }
}

uploadVideoAndCreateAds() {
  if (this.selectedVideo) { // Check if selectedVideo is not null
    const formData = new FormData();
    formData.append('file', this.selectedVideo);
    this.adsService.uploadVideo(formData).subscribe({
      next: (response: any) => {
        this.ads.video = response.videoUrl;
        this.createAds();
      },
      error: (error: any) => {
        console.error('Failed to upload video:', error);
        this.toastr.error('There was a problem uploading the video.', 'Upload Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  } else {
    this.createAds();
  }
}

  createAds() {
    this.adsService.createAds(this.ads).subscribe({
      next: (response: any) => {
        this.toastr.success('The ads was added successfully!', 'Add Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });      
        this.router.navigate(['admin/ads/list-ads']);       
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
