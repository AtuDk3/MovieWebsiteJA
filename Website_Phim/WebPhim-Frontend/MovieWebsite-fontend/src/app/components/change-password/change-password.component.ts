import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { TokenService } from '../../service/token.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})

export class ChangePasswordComponent {
  @ViewChild('changePasswordForm') changePasswordForm!: NgForm;
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
  currentPasswordError: string;
  passwordStrength: string;

  constructor(private userService: UserService, private tokenService: TokenService, private router: Router,) {
    if (!this.tokenService.getToken()) {
      this.router.navigate(['']);
    }
    this.currentPassword='';
    this.newPassword='';
    this.confirmPassword='';
    this.currentPasswordError='';
    this.passwordStrength='';
  }

  checkPasswordMatch() {
    if (this.newPassword !== this.confirmPassword) {
      this.changePasswordForm.form.controls['retypePassword'].setErrors({ 'passwordMisMatch': true });
    } else {
      this.changePasswordForm.form.controls['retypePassword'].setErrors(null);
    }
  }

  changePassword() {
    // Kiểm tra mật khẩu hiện tại có đúng không
    this.userService.checkCurrentPassword(this.currentPassword)
      .subscribe({
        next: () => {
          
          // Nếu mật khẩu hiện tại đúng, kiểm tra độ mạnh của mật khẩu mới
          if (this.isStrongPassword(this.newPassword)) {
            // Gửi yêu cầu cập nhật mật khẩu mới nếu mật khẩu mới đủ mạnh
            this.userService.changePassword(this.newPassword)
              .subscribe({
                next: response => {
                  debugger
                  // Xử lý response từ server                 
                  // Reset form
                  this.resetForm();
                  this.router.navigate(['']);
                },
                error: err => {
                  // Xử lý lỗi nếu có
                  console.error(err);
                }
              });
          } else {
            this.passwordStrength = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.";
          }
        },
        error: err => {
          // Nếu mật khẩu hiện tại không đúng, hiển thị thông báo lỗi
          this.currentPasswordError = "Incorrect current password.";
        }
      });
    
  }

  isStrongPassword(password: string): boolean {
    // Kiểm tra mật khẩu có đủ mạnh không
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}$/;
    return regex.test(password);
  }

  resetForm() {
    this.currentPassword = '';
    this.newPassword = '';
    this.confirmPassword = '';
    this.currentPasswordError = '';
    this.passwordStrength = '';
    this.changePasswordForm.resetForm();
  }
}
