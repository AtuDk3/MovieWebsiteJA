
import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { BookmarkService } from '../../services/bookmark.service';
import { AuthService } from '../../services/auth.service';
import { AuthGGService } from '../../services/auth-gg.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;
  isAccept: boolean;
  userResponse?: UserResponse;
  errorPass: string | null = null;
  errorActive: string | null = null;

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService,
    private bookmarkService: BookmarkService, private authService: AuthService,
    private authGGService: AuthGGService
  ) {
    this.phoneNumber = '';
    this.password = '';
    this.isAccept = false;
  }

  onPhoneNumberChange() {
    console.log(this.phoneNumber);
  }

  login() {
    const loginDTO = {
      "phone_number": this.phoneNumber,
      "password": this.password
    }
    this.userService.login(loginDTO).subscribe(
      {
        next: (response: LoginResponse) => {
          debugger
          const { token } = response;
          console.log(token);
          this.tokenService.setToken(token);
          this.bookmarkService.resetBookmarkCount();
          this.userService.removeVipPeriodFromLocalStorage();
          debugger
          this.userService.getUserDetails(token).subscribe({
            next: (response: any) => {
              debugger
              this.userResponse = {
                ...response
              }
              if (this.userResponse) {               
                this.authService.setUserId(this.userResponse.id);
                this.authService.isLoggedInSource.next(true);
              }
              this.userService.saveUserResponseToLocalStorage(this.userResponse);
              if (this.userResponse?.role.name === 'Admin') {
                this.router.navigate(['/admin']);
              } else {
                this.router.navigate(['/']);
              }

            },
            complete: () => {
            },
            error: (error: any) => {
              alert(error.error.message);
            }
          })
        },
        complete: () => {
          debugger
        },
        error: (err) => {
          this.errorPass = null;
          this.errorActive = null;

          if (err.error.errorPass) {
            this.errorPass = err.error.errorPass;
          }
          if (err.error.errorActive) {
            this.errorActive = err.error.errorActive;
          }
        }
      }
    )
  }

  loginGG(){
    this.authGGService.loginGG();
  }


}