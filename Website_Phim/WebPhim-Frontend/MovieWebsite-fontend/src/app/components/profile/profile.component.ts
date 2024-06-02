import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../service/token.service';
import { Router } from '@angular/router';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { UpdateUserDTO } from '../../dtos/user/updateuser.dto';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  userResponse: UserResponse | null = null;
  selectedFile: File | null = null;
  imageUrl: SafeUrl | string | null = null;
  fullName: string | null = null;
  email: string | null = null;
  phoneNumber: string | null = null;
  password: string | null = null;
  userId: number = 0;

  @ViewChild('changePasswordForm') changePasswordForm!: NgForm;
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
  currentPasswordError: string;
  passwordStrength: string;


  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private route: ActivatedRoute
  ) {   
    if (this.tokenService.getToken()===null) {
      this.router.navigate(['']);
    }
    this.currentPassword='';
    this.newPassword='';
    this.confirmPassword='';
    this.currentPasswordError='';
    this.passwordStrength='';
  }

  ngOnInit() {     
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam !== null) {
        this.userId = Number(idParam);
      }
    });
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    if (this.userResponse?.id === this.userId) {
      this.fullName = this.userResponse.full_name;
      this.email = this.userResponse.email;
      this.phoneNumber = this.userResponse.phone_number;
      this.loadImage(`${this.userResponse?.img_avatar}`);     
    }else{
      this.router.navigate(['']);
    }
    
  }

  loadImage(imageName: string) {
    this.userService.getImage(imageName).subscribe({
      next: (imageBlob: Blob) => {
        const objectURL = URL.createObjectURL(imageBlob);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      error: (error) => {
        console.error('Error loading image', error);
      },
      complete: () => {
        console.log('Image loading complete');
      }
    });
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    debugger
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = e => this.imageUrl = reader.result;
      reader.readAsDataURL(file);
      this.userService.uploadAvatar(this.userId, file).subscribe({
        next: (response) => {
          console.log('File uploaded successfully', response);
          // Handle response here if needed
        },
        error: (error) => {
          console.error('Error uploading file', error);
          // Handle error here
        }
      });
    }
  }

  update() {
    
    const updateUserDTO: UpdateUserDTO = {
      full_name: this.fullName!,
      phone_number: this.phoneNumber!,
      password: this.password!,
      email: this.email!,   
    };
    this.userService.updateUserDetails(this.userId, updateUserDTO, this.tokenService.getToken()!)
      .subscribe({
        next: (response: any) => {
          this.userResponse = {
            ...response,
            date_of_birth: new Date(response.date_of_birth),
            created_at: new Date(response.created_at)
          };
          if (this.userResponse) {
            const day = ('0' + new Date(this.userResponse.created_at).getDate()).slice(-2);
            const month = ('0' + (new Date(this.userResponse.created_at).getMonth() + 1)).slice(-2);
            const year = new Date(this.userResponse.created_at).getFullYear();
            const formattedDate = `${day}/${month}/${year}`;
            this.userResponse.created_at_formatted = formattedDate;

            const day_of_birth = ('0' + new Date(this.userResponse.date_of_birth).getDate()).slice(-2);
            const month_of_birth = ('0' + (new Date(this.userResponse.date_of_birth).getMonth() + 1)).slice(-2);
            const year_of_birth = new Date(this.userResponse.date_of_birth).getFullYear();
            const formatted_of_birth = `${day_of_birth}/${month_of_birth}/${year_of_birth}`;
            this.userResponse.date_of_birth_formatted = formatted_of_birth;
            this.userService.removeUserFromLocalStorage();
            this.userService.saveUserResponseToLocalStorage(this.userResponse);
            window.location.reload(); 
          }         
          
        },
        error: (error: any) => {
          console.error('Error updating user details', error);
        },
        complete: () => {
          console.log('Update user details request completed.');
        }
      });
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
  
  showUpdateForm: boolean = false;
  changePassForm: boolean = false;

  toggleUpdateForm() {
    this.showUpdateForm = !this.showUpdateForm;
  }

  togglechangePassword() {
    this.changePassForm = !this.changePasswordForm;
  }

  toggleInforUser() {
    this.changePassForm = false;
    this.showUpdateForm= false;
  }
}
