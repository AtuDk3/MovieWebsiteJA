import { Component } from '@angular/core';
import { AdsService } from '../../../services/ads.service';
import { Ads } from '../../../models/ads';
import { Router } from '@angular/router';

@Component({
  selector: 'app-check-trading-code',
  templateUrl: './check-trading-code.component.html',
  styleUrl: './check-trading-code.component.scss'
})
export class CheckTradingCodeComponent {

  message: string = '';
  trading_code: string = '';
  isError: boolean = false;
  adsResponse: Ads | null = null;
  constructor(private adsService: AdsService,
    private router: Router
  ) { }

  checkTradingCode() {
    debugger
    this.adsService.checkTradingCode(this.trading_code).subscribe({
      next: response => {
          this.adsResponse= response;
          if(this.adsResponse){
            this.router.navigate(['/payment-ads'], { 
              queryParams: { amount: this.adsResponse.amount, trading_code: this.adsResponse.trading_code} });
          }
      },
      error: error => {
        this.message = 'Mã giao dịch không hợp lệ';
        this.isError = true;      
      }
    });
  } 
  
}