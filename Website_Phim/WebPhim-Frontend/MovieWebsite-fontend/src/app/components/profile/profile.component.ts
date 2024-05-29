import { Component } from '@angular/core';
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
  

  constructor(
    private userService: UserService,
    private tokenService: TokenService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();
    if (this.userResponse) {
      this.fullName = this.userResponse.full_name;
      this.email = this.userResponse.email;
      this.phoneNumber = this.userResponse.phone_number;
      this.password = this.userResponse.password;
    }
  }

  ngOnInit() {
    if (!this.tokenService.getToken()) {
      this.router.navigate(['']);
    }
    
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam !== null) {
        this.userId = Number(idParam);
      }
    });
    this.loadImage(`${this.userResponse?.img_avatar}`);
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
        next: (response) => {
          debugger
          this.userResponse = {
            ...response,
            date_of_birth: new Date(response.date_of_birth),
          }
          this.userService.removeUserFromLocalStorage();
          this.userService.saveUserResponseToLocalStorage(this.userResponse!);
          window.location.reload(); 
        },
        error: (error: any) => {
          console.error('Error updating user details', error);
        },
        complete: () => {
          console.log('Update user details request completed.');
        }
      });
  }
}
