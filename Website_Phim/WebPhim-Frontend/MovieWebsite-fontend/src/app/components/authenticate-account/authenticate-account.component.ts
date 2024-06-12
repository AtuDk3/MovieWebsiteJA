import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { RegisterDTO } from '../../dtos/user/register.dto';
@Component({
  selector: 'app-authenticate-account',
  templateUrl: './authenticate-account.component.html',
  styleUrl: './authenticate-account.component.scss'
})
export class AuthenticateAccountComponent {
  message: string = '';
  otp: string = '';
  check_otp: string= '';
  countdown: number=0;
  isError: boolean = false;
  countdownInterval: any;
  registerData: any;

  constructor(private userService: UserService, private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras.state) {
      this.registerData = navigation.extras.state['registerData'];
    }
    this.startCountdown();  
    this.onSubmit();
   }

  onSubmit() {
    this.userService.authenticate_account(this.registerData.full_name, this.registerData.email).subscribe({
      next: response => {
        this.check_otp = response['check_otp'];                        
      },
      error: error => {
        console.log("Lỗi xác thực email account");
        this.router.navigate(['/register'])       
      }
    });
  }

  startCountdown(): void {
    this.countdown = 20; 
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
    this.startCountdown();  
    this.onSubmit();
  }

  checkOTP(){
        if(this.otp === this.check_otp){    
          this.isError = false;   
          
          this.userService.register(this.registerData).subscribe(
            {
              next: (response: any) => {               
                this.router.navigate(['/login']);              
            },
            complete: () => {
      
            },
            error: (error: any) => {
              console.log("Register fail")
            }}
          )    
        }else{
          this.message = 'OTP không hợp lệ';
          this.isError = true;
        }     
    
  }
}