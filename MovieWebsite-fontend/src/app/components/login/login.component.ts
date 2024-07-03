
import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import {LoginResponse} from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { BookmarkService } from '../../services/bookmark.service';
import { AuthService } from '../../services/auth.service';



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
  userResponse ?: UserResponse;
  errorPass: string | null = null;
  errorActive: string | null = null;

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService,
   private bookmarkService: BookmarkService, private authService: AuthService
   ) {
    this.phoneNumber = '';
    this.password = '';
    this.isAccept = false;
  }

  onPhoneNumberChange(){
    console.log(this.phoneNumber);
  }

  login(){
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
              next: (response: any) =>{
                debugger               
                this.userResponse={
                  ... response                                
               } 
                if (this.userResponse) {                  
                  const day =  ('0' + (new Date(this.userResponse.created_at).getDate())).slice(-2);
                  const month = ('0' + (new Date(this.userResponse.created_at).getMonth() +1 )).slice(-2); 
                  const year = new Date(this.userResponse.created_at).getFullYear();
                  const formattedDate = `${day}/${month}/${year}`;
                  this.userResponse.created_at_formatted = formattedDate;
                  const day_of_birth = ('0' + (new Date(this.userResponse.date_of_birth).getDate())).slice(-2);
                  const month_of_birth = ('0' + (new Date(this.userResponse.date_of_birth).getMonth() +1 )).slice(-2);
                  const year_of_birth = new Date(this.userResponse.date_of_birth).getFullYear();
                  const formatted_of_birth = `${day_of_birth}/${month_of_birth}/${year_of_birth}`;
                  this.userResponse.date_of_birth_formatted = formatted_of_birth;    
                  this.authService.setUserId(this.userResponse.id);
                  this.authService.isLoggedInSource.next(true);       
              }             
                  this.userService.saveUserResponseToLocalStorage(this.userResponse);               
                 if(this.userResponse?.role.name === 'Admin'){
                   this.router.navigate(['/admin']);
                 }else{
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
}