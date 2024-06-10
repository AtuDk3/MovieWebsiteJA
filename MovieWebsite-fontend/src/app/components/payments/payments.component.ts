import { Component } from '@angular/core';
import { VnpayService } from '../../services/vnpay.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.scss'
})
export class PaymentComponent {

  orderId: string = '';
  amount: string = '';

  constructor(private vnpayService: VnpayService) { }

  onSubmit() {
    this.vnpayService.createPayment(this.orderId, this.amount).subscribe(paymentUrl => {
      window.location.href = paymentUrl; // Redirect to VNPay payment URL
    });
  }
}
