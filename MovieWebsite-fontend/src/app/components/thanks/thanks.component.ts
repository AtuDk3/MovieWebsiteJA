import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VnpayService } from '../../services/vnpay.service';

@Component({
  selector: 'app-thanks',
  templateUrl: './thanks.component.html',
  styleUrls: ['./thanks.component.scss']
})
export class ThanksComponent {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vnpayService: VnpayService
  ) { }

  // ngOnInit(): void {
  //   this.route.queryParams.subscribe(params => {

  //     this.vnpayService.paymentCompleted().subscribe({
  //       next: (response: string) => {
  //         if (response.startsWith('redirect:')) {
  //           const redirectUrl = response.substring('redirect:'.length);
  //           window.location.href = redirectUrl;
  //         } else {
  //           console.error('Invalid response:', response);
  //         }
  //       },
  //       error: (error) => {
  //         console.error('Payment request failed', error);
  //       }
  //     });
  //     });
  //   };
 
}
