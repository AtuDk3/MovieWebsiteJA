import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../service/register.service';

@Component({
  selector: 'app-verify-email',
  template: '<p>{{ message }}</p>',
  styles: []
})
export class VerifyEmailComponent implements OnInit {
  message: string = '';

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const token = this.route.snapshot.queryParamMap.get('token');
    if (token) {
      this.authService.verifyEmail(token).subscribe(
        response => {
          this.message = 'Email verified successfully.';
        },
        error => {
          this.message = 'Invalid or expired token.';
        }
      );
    } else {
      this.message = 'No token provided.';
    }
  }
}