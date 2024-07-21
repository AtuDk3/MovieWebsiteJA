import { Component, OnInit } from '@angular/core';
import { AdsDTO } from '../../../../dtos/user/ads.dto';
import { AdsService } from '../../../../services/ads.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ads } from '../../../../models/ads';

@Component({
  selector: 'app-create-order-ads',
  templateUrl: './create-order-ads.component.html',
  styleUrl: './create-order-ads.component.scss'
})
export class CreateOrderAdsComponent implements OnInit{

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
  ads_id: number = 0;
  adsResponse: Ads | null = null;

  constructor(
    private adsService: AdsService,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.ads_id = Number(id);     
    }
  }

  createOrderAds() {
    this.adsService.createOrderAds(this.ads_id, this.ads).subscribe({
      next: (response: any) => {
        this.toastr.success('The ads was create order successfully!', 'Add Success', {
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
        this.toastr.error('There was a problem create order the ads.', 'Add Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  }

}
