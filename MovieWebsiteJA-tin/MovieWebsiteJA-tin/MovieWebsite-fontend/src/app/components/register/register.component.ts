import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { RegisterDTO } from '../../dtos/user/register.dto';
import { NavigationExtras } from '@angular/router';

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
  emailError: string | null = null;
  phoneError: string | null = null;
  passwordStrengthError: string | null = null;

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

  checkPasswordStrength() {
    const strongPasswordPattern = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
    if (!strongPasswordPattern.test(this.password)) {
      this.passwordStrengthError = "Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a number, and a special character.";
    } else {
      this.passwordStrengthError = null;
    }
  }

  checkAge(){
    if (this.dob) {
      const today = new Date();
      const dob = new Date(this.dob);
      let age = today.getFullYear() - dob.getFullYear();
      const monthDiff = today.getMonth() - dob.getMonth();
      const dayDiff = today.getDate() - dob.getDate();

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
        "date_of_birth": this.dob,
        "email": this.email
    }
    this.userService.checkRegister(registerDTO).subscribe(
      {
        next: (response: any) => {
          const navigationExtras: NavigationExtras = {
            state: {
              registerData: registerDTO
            }
          };
        
          this.router.navigate(['/authenticate-account'], navigationExtras);
      },
      complete: () => {
      },
      error: (err) => {
        this.emailError = null;
        this.phoneError = null;

        if (err.error.emailError) {
          this.emailError = err.error.emailError;
        }
        if (err.error.phoneError) {
          this.phoneError = err.error.phoneError;
        }
      }
    })
  }

}