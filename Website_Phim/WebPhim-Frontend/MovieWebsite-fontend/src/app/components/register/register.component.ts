import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { RegisterDTO } from '../../dtos/register.dto';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
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
//private http: HttpClient, private router: Router
  constructor(private router: Router, private userService: UserService) {
    this.fullName='huy';
    this.email = 'huykvde170548@fpt.edu.vn';
    this.phoneNumber = '0763583081';
    this.dob = new Date();
    this.dob.setFullYear(this.dob.getFullYear()-18);
    this.password = '123';
    this.retypePassword = '123';
    this.isAccept = true;
  }

  onEmailChange(){
    console.log(this.email);
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
    //alert("Success!");
    
    const registerDTO:RegisterDTO={
      "full_name": this.fullName,
      "phone_number": this.phoneNumber,
      "password": this.password,
      "retype_password": this.retypePassword,
      "date_of_birth": this.dob,
      "email": this.email,
      "role_id": 1,
      "vip_id": 2
    }
    this.userService.register(registerDTO).subscribe({
      next : (response : any) => {
        this.router.navigate(['login']);
      },
      complete: () => {

      },
      error: (error: any)=>{
        alert(`Cannot register, error: ${error.error}`)
        
      }      
       
  });
    
  
  }
}