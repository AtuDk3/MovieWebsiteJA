import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent implements OnInit{
  newPassword: string = '';
  confirmPassword: string = '';
  message: string = '';
  email: string | null = null;
  isError: boolean = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
      this.email = this.userService.getEmail();  
  }

  onSubmit() {
    if (this.newPassword !== this.confirmPassword) {
      this.message = 'Mật khẩu xác nhận không khớp';
      this.isError = true;
      return;
    }

    if (!this.isPasswordStrong(this.newPassword)) {
      this.message = 'Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt';
      this.isError = true;
      return;
    }

    this.userService.resetPassword(this.email!, this.newPassword).subscribe({
      next: response => {
        this.message = 'Đặt lại mật khẩu thành công';
        this.isError = false;
        this.userService.removeEmail();
        this.router.navigate(['/login']);
        
      },
      error: error => {
        this.message = 'Đặt lại mật khẩu thất bại';
        this.isError = true;
        
      }
    });
  }

  private isPasswordStrong(password: string): boolean {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordRegex.test(password);
  }
}
