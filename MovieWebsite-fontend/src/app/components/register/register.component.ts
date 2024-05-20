import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})

export class RegisterComponent {
  email: string;
  phoneNumber: string;
  dob: Date;
  password: string;
  retypePassword: string;
  isAccept: boolean;

  constructor() {
    this.email = '';
    this.phoneNumber = '';
    this.dob = new Date();
    this.password = '';
    this.retypePassword = '';
    this.isAccept = false;
  }

  onEmailChange(){
    console.log(this.email);
  }

  register(){
    alert("Success!");
  }

}
