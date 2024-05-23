import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { RegisterDTO } from '../../dtos/user/register.dto';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  fullName: string;
  email: string;
  phoneNumber: string;
  dob: Date;
  password: string;
  retypePassword: string;
  isAccept: boolean;

  constructor(private router: Router, private userService: UserService) {
    this.fullName = 'huy hhhhh';
    this.email = 'huykvde170548@fpt.edu.vn';
    this.phoneNumber = '0763583007';
    this.dob = new Date();
    this.dob.setFullYear(this.dob.getFullYear()-18);
    this.password = '123';
    this.retypePassword = '123';
    this.isAccept = true;
  }

  
  

  onEmailChange(){
    console.log(this.email);
  }

  onFullNameChange(){
    console.log(this.fullName);
  }

  onPhoneNumberChange(){
    console.log(this.phoneNumber);
  }

  checkPasswordMatch(){
    if(this.password !== this.retypePassword){
      this.registerForm.form.controls['retypePassword'].setErrors({'passwordMisMatch': true});
    }
    else{
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }

  checkAge(){
    if (this.dob) {
      const today = new Date();
      const dob = new Date(this.dob);
      let age = today.getFullYear() - dob.getFullYear();
      const monthDiff = today.getMonth() - dob.getMonth();
      const dayDiff = today.getDate() - dob.getDate();

      // Adjust age if the birthdate hasn't occurred yet this year
      if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
        age--;
      }

      if (age < 5) {
        this.registerForm.form.controls['dob'].setErrors({ 'invalidAge': true });
      } else {
        this.registerForm.form.controls['dob'].setErrors(null);
      }
    }
  }

  register(){
    //debugger
    const registerDTO:RegisterDTO = {
        "full_name": this.fullName,
        "phone_number": this.phoneNumber,
        "password": this.password,
        "retype_password": this.retypePassword,
        "date_of_birth": this.dob,
        "email": this.email,
        "role_id": 1,
      "vip_id": 2
    }
    this.userService.register(registerDTO).subscribe(
      
      //   next: (response: any) => {
      //     //debugger
      //     if(response && (response.status === 200 || response.status === 201)) {
      //     this.router.navigate(['/login']);
      //   } else {
  
      //   }
      // },
      
      // complete: () => {
      //   debugger
      // },
      
      // error: (error: any) => {
      //   console.log('Registration failed!', error);
      // }

      response => {
        debugger
        console.log(response);
        alert('Please check your email for verification.');
      },
      error => {
        console.error(error);
        alert('Registration failed.');
      }
    );
  }

}
