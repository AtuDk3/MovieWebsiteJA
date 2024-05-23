import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import {LoginResponse} from '../../responses/user/login.response';
import {TokenService} from '../../service/token.service';

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

  constructor(private router: Router, private userService: UserService, private tokenService: TokenService) {
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
          if(response && response.message) {
            const {token} = response;
            console.log(token);
            this.tokenService.setToken(token);
           this.router.navigate(['']);
        } else {
          
        }
      },
      complete: () => {
      },
      error: (error: any) => {
        this.loginForm.form.controls['password'].setErrors({'wrongPassword': true});
      }}
    )
  }
}
