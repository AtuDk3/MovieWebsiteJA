import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
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
    this.fullName = '';
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

    const registerDTO:RegisterDTO = {
        "full_name": this.fullName,
        "phone_number": this.phoneNumber,
        "password": this.password,
        "retype_password": this.retypePassword,
        "date_of_birth": this.dob,
        "email": this.email
    }
    this.userService.register(registerDTO).subscribe(
      {
        next: (response: any) => {
          if(response && response.message) {
          this.router.navigate(['/login']);
        } else {
  
        }
      },
      complete: () => {

      },
      error: (error: any) => {
        this.registerForm.form.controls['phoneNumber'].setErrors({'existPhoneNumber': true});
      }}
    )
  }

}