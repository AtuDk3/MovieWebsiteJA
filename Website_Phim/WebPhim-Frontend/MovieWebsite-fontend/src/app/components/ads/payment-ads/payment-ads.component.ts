import { Component, OnInit } from '@angular/core';
import { VnpayService } from '../../../services/vnpay.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-payment-ads',
  templateUrl: './payment-ads.component.html',
  styleUrl: './payment-ads.component.scss'
})
export class PaymentAdsComponent implements OnInit{
  amount: number = 0;
  trading_code: string = '';

  constructor(private route: ActivatedRoute,
    private vnpayService: VnpayService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.amount = +params['amount'];
      this.trading_code = params['trading_code'];
    });
  }

  onSubmit() {
    this.vnpayService.createPaymentAds(this.amount, this.trading_code).subscribe({
      next: (response: string) => {
        if (response.startsWith('redirect:')) {
          const redirectUrl = response.substring('redirect:'.length);
          window.location.href = redirectUrl;
        } else {
          console.error('Invalid response:', response);
        }
      },
      error: (error) => {
        console.error('Payment request failed', error);
      }
    });
  }
}
