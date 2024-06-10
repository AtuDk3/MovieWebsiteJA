import { Component } from '@angular/core';
import { VnpayService } from '../../services/vnpay.service';
import { VNPayResponse } from '../../responses/user/vn_pay.response';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.scss']
})
export class PaymentComponent {
  amount: string = '';
  bankCode: string = '';

  constructor(private vnpayService: VnpayService) { }

  onSubmit() {
    this.vnpayService.createPayment(this.amount, this.bankCode).subscribe({
      next: (response: VNPayResponse) => {
        // Kiểm tra xem response và paymentUrl có tồn tại không
        if (response && response.paymentUrl) {
          // Tách các phần không cần mã hóa
          const { baseUrl, queryString } = this.splitPaymentUrl(response.paymentUrl);

          // Tạo lại query string với vnp_SecureHash đã được mã hóa
          const encodedQueryString = this.encodeSecureHash(queryString);

          // Tạo lại paymentUrl với baseUrl và query string mới
          const encodedPaymentUrl = `${baseUrl}?${encodedQueryString}`;

          // Redirect đến URL đã mã hóa
          window.location.href = encodedPaymentUrl;
        } else {
          console.error('Invalid response or payment URL not found:', response);
        }
      },
      error: (error) => {
        console.error('Payment request failed', error);
      }
    });
  }

  // Hàm tách baseUrl và query string
  private splitPaymentUrl(paymentUrl: string): { baseUrl: string; queryString: string } {
    const parts = paymentUrl.split('?');
    const baseUrl = parts[0];
    const queryString = parts[1] || '';
    return { baseUrl, queryString };
  }

  // Hàm mã hóa vnp_SecureHash
  private encodeSecureHash(queryString: string): string {
    const params = new URLSearchParams(queryString);
    const vnp_SecureHash = params.get('vnp_SecureHash') || '';
    const encodedSecureHash = encodeURIComponent(vnp_SecureHash);
    params.set('vnp_SecureHash', encodedSecureHash);
    return params.toString();
  }
}
