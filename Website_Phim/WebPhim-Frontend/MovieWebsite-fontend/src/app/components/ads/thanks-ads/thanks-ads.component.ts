import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdsService } from '../../../services/ads.service';
import { Ads } from '../../../models/ads';

@Component({
  selector: 'app-thanks-ads',
  templateUrl: './thanks-ads.component.html',
  styleUrl: './thanks-ads.component.scss'
})
export class ThanksAdsComponent implements OnInit{

  trading_code= '';
  adsResponse: Ads | null =null;

  constructor(
    private route: ActivatedRoute,
    private adsService: AdsService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {      
      this.trading_code = (params['vnp_OrderInfo']);
    });
    this.adsService.updateAdsPayment(this.trading_code).subscribe({
      next: response => {
          this.adsResponse= response;          
      },
      error: error => {
            
      }
    });
  }

}
