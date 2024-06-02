import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import {LoginResponse} from '../../responses/user/login.response';
import {TokenService} from '../../service/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { environment } from '../../environments/environment';


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

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService,
   
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
              }             
                  this.userService.saveUserResponseToLocalStorage(this.userResponse); 
                 if(this.userResponse?.role.name === 'Admin'){
                   this.router.navigate(['/admin']);
                 }else{
                  this.router.navigate(['']);
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
      error: (error: any) => {
        this.loginForm.form.controls['password'].setErrors({'wrongPassword': true});
      }}
    )
  }
}