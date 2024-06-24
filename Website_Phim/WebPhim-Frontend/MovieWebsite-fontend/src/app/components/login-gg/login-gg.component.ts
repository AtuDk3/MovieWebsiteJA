import { Component, OnInit } from '@angular/core';
import { AuthGGService } from '../../services/auth-gg.service';
import { LoginGGDTO } from '../../dtos/user/logingg.dto';
import { UserService } from '../../services/user.service';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-gg',
  templateUrl: './login-gg.component.html',
  styleUrl: './login-gg.component.scss'
})
export class LoginGgComponent implements OnInit{

  userResponse: UserResponse | null = null;
  constructor(
    private authGGService: AuthGGService,
    private userService: UserService,
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) {    
  }

  ngOnInit() {
    this.getDataLoginGG();
  }

  getDataLoginGG() {
    const data = JSON.stringify(this.authGGService.getProfile());
    const profile = JSON.parse(data);
    const email = profile.email;
    const emailVerified = profile.email_verified;
    const name = profile.name;
    const picture = profile.picture;
    //const birthday = profile.birthday; // Lấy thông tin ngày sinh nếu có
    console.log(email)
    if (email && emailVerified === true) {
      const loginGGDTO: LoginGGDTO = {
        "full_name": name,
        "phone_number": '',
        "password": '',
        "date_of_birth": new Date,     
        "email": email,
        "img_avatar": picture
      }
        this.userService.loginGG(loginGGDTO)
          .subscribe({
            next: (response: LoginResponse) => {
              debugger
              const { token } = response;
              this.tokenService.setToken(token);
              this.userService.getUserDetails(token).subscribe({
                next: (response: any) => {
                  debugger
                  this.userResponse = {
                    ...response
                  }
                  if (this.userResponse) {               
                    this.authService.setUserId(this.userResponse.id);
                    this.authService.isLoggedInSource.next(true);
                    this.userService.saveUserResponseToLocalStorage(this.userResponse);
                  }                 
                  if (this.userResponse?.role.name === 'Admin') {
                    this.router.navigate(['/admin']);
                  } else {
                    this.router.navigate(['/']).then(() => {
                      window.location.href = '/';
                    });
                  }
    
                },
                complete: () => {
                },
                error: (error: any) => {
                  alert(error.error.message);
                }
              })
            },
            complete: () => {           
            },
            error: (err) => {
              console.log('Error login gg')
            }
          });
      }

    }


}
