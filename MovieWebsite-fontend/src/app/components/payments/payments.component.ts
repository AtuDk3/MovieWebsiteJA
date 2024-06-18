import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VnpayService } from '../../services/vnpay.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentComponent implements OnInit {

  amount: number = 0;
  vip_name: string = '';

  constructor(private route: ActivatedRoute,
    private vnpayService: VnpayService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.amount = +params['amount'];
      this.vip_name = params['vip_name'];
    });
  }

  onSubmit() {
    this.vnpayService.createPayment(this.amount, this.vip_name).subscribe({
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