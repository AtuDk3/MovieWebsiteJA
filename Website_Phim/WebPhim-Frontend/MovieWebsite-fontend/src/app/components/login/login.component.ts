import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

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

  constructor(private router: Router, private userService: UserService) {
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
        next: (response: any) => {
          if(response==="ok") {
            alert("ok");
           this.router.navigate(['']);
        } else {
          alert("error");
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
      },
      error: (error: any) => {
        console.log('Registration failed!', error);
      }}
    )
  }
}