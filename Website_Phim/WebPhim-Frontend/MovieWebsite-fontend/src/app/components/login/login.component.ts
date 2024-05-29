import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import {LoginResponse} from '../../responses/user/login.response';
import {TokenService} from '../../service/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { environment } from '../../environments/environments';

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

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService ) {
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
          //if(response && response.message) {      
            console.log(token);
            this.tokenService.setToken(token);
            debugger
            this.userService.getUserDetails(token).subscribe({
              next: (response: any) =>{
                debugger
                // this.userResponse= {
                //   id: response.id,
                //   full_name: response.full_name,
                //   is_active: response.is_active,
                //   date_of_birth: new Date (response.date_of_birth),
                //   facebook_account_id: response.facebook_account_id,
                //   google_account_id: response.google_account_id,
                //   role: response.role
                // }
                this.userResponse={
                  ... response,
                  date_of_birth: new Date (response.date_of_birth),                  
                }              
                this.userService.saveUserResponseToLocalStorage(this.userResponse);   
                this.router.navigate(['']);
              },
              complete: () => {
              },
              error: (error: any) => {               
                alert(error.error.message);
              }
            })
           
        //} else {
          
        //}
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
        this.loginForm.form.controls['password'].setErrors({'wrongPassword': true});
      }}
    )
  }
}
