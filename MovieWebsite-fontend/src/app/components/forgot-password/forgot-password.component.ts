
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.scss'
})
export class ForgotPasswordComponent {
  email: string = '';
  message: string = '';
  otp: string = '';
  check_otp: string= '';
  countdown: number=0;
  isError: boolean = false;
  formCheckOTP: boolean = false;
  countdownInterval: any;

  constructor(private userService: UserService, private router: Router) { }

  onSubmit() {
    debugger
    this.userService.forgotPassword(this.email).subscribe({
      next: response => {
        this.check_otp = response['check_otp'];
        this.userService.setEmail(this.email);
        this.message = 'Vui lòng kiểm tra email để đặt lại mật khẩu';
        this.isError = false;
        this.formCheckOTP= true;    
        this.startCountdown();   
      },
      error: error => {
        this.message = 'Email không hợp lệ';
        this.isError = true;
       
      }
    });
  }

  startCountdown(): void {
    this.countdown = 15; 
    this.countdownInterval = setInterval(() => {
      this.countdown--;
      if (this.countdown <= 0) {
        clearInterval(this.countdownInterval);
      }
    }, 1000);
  }

  resetCountdown(): void {
    if (this.countdownInterval) {
      clearInterval(this.countdownInterval);
    }
    this.countdown = 0;
  }

  resendRequest(): void {
    this.resetCountdown();
    this.onSubmit();
  }

  checkOTP(){
        if(this.otp === this.check_otp){    
          this.isError = false;
          this.formCheckOTP= true;    
          this.router.navigate(['/reset-password'], { queryParams: {email: this.userService.getEmail() } });
        }else{
          this.message = 'OTP không hợp lệ';
          this.isError = true;
        }     
    
  }
}
