import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { VnpayService } from '../../services/vnpay.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentComponent {
  amount: string = '10000'; 
  orderInfo: string = 'thanhtoan'; 

  constructor(private vnpayService: VnpayService) {}

  onSubmit() {
    this.vnpayService.createPayment(this.amount, this.orderInfo).subscribe({
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
