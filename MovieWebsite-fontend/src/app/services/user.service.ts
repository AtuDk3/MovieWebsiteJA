
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { UserResponse } from '../responses/user/user.response';
import { UpdateUserDTO } from '../dtos/user/updateuser.dto';
import { VipPeriodResponse } from '../responses/user/vip_period.response';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;
  private apiUserImg = `${environment.apiBaseUrl}/users/images`;
  private apiUser = `${environment.apiBaseUrl}/users`;
  private apiForgotPassword = `${environment.apiBaseUrl}/users/forgot-password`;
  private apiResetPassword = `${environment.apiBaseUrl}/users/reset-password`;
  private apiCheckOTP = `${environment.apiBaseUrl}/users/check-otp`;
  private apiVipPeriod = `${environment.apiBaseUrl}/users/vip_period`;

  private apiConfig = {
    headers: this.createHeader(),
  }

  constructor(private http: HttpClient) { }

  private createHeader(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      //'Accepted-Language': 'en'
    });
  }

  register(registerData: any): Observable<any> {
    return this.http.post(this.apiRegister, registerData, this.apiConfig);
  }

  login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(this.apiLogin, loginDTO, this.apiConfig);
  }

  updateUserDetails(userId: number, userUpdateDTO: UpdateUserDTO, token: string): Observable<any> {
    const url = `${this.apiUserDetail}/${userId}`;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.put(url, userUpdateDTO, { headers: headers });
  }

  getUserDetails(token: string) {
    return this.http.post(this.apiUserDetail, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      })
    })
  }


  saveUserResponseToLocalStorage(userResponse?: UserResponse) {
    try {
      if (userResponse == null || !userResponse) {
        return;
      }
      const userResponseJSON = JSON.stringify(userResponse);
      localStorage.setItem('user', userResponseJSON);

      console.log('User response saved to local storage');
    } catch (error) {
      console.error('Error saving user response to local storage:', error);
    }
  }

  getUserResponseFromLocalStorage(): UserResponse | null {
    try {
      const userResponseJSON = localStorage.getItem('user');
      if (userResponseJSON == null || userResponseJSON == undefined) {
        return null;
      }
      const userResponse = JSON.parse(userResponseJSON!);
      console.log('User response retrieved from local storage');
      return userResponse;
    } catch (error) {
      console.log('Error retrieved user response from local storage:', error);
      return null;
    }
  }

  removeUserFromLocalStorage(): void {
    try {
      localStorage.removeItem('user');
      console.log('User data removed from local storage')
    } catch (error) {
      console.log(`Error removing user data from local storage:`, error);
    }
  }

  saveVipPeriodResponseToLocalStorage(vipPeriodResponse?: VipPeriodResponse) {
    try {
      if (vipPeriodResponse == null || !vipPeriodResponse) {
        return;
      }
      const vipPeriodResponseJSON = JSON.stringify(vipPeriodResponse);
      localStorage.setItem('vip_period', vipPeriodResponseJSON);

      console.log('Vip Period response saved to local storage');
    } catch (error) {
      console.error('Error saving Vip Period response to local storage:', error);
    }
  }

  getVipPeriodResponseFromLocalStorage(): VipPeriodResponse | null {
    try {
      const vipPeriodResponseJSON = localStorage.getItem('vip_period');
      if (vipPeriodResponseJSON == null || vipPeriodResponseJSON == undefined) {
        return null;
      }
      const vipPeriodResponse = JSON.parse(vipPeriodResponseJSON!);
      console.log('Vip Period response retrieved from local storage');
      return vipPeriodResponse;
    } catch (error) {
      console.log('Error retrieved Vip Period response from local storage:', error);
      return null;
    }
  }

  removeVipPeriodFromLocalStorage(): void {
    try {
      localStorage.removeItem('vip_period');
      console.log('Vip Period data removed from local storage')
    } catch (error) {
      console.log(`Error removing Vip Period data from local storage:`, error);
    }
  }

  getImage(imageName: string): Observable<Blob> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + localStorage.getItem('access_token') // Lấy token từ local storage
    });
    return this.http.get(`${this.apiUserImg}/${imageName}`, { headers: headers, responseType: 'blob' });
  }


  uploadAvatar(userId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(`${this.apiUser}/upload_avatar/${userId}`, formData);
  }

  checkCurrentPassword(password: string): Observable<any> {
    const url = `${this.apiUser}/checkCurrentPassword`;
    return this.http.post(url, { password }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
      })
    });
  }

  changePassword(password: string): Observable<any> {
    const url = `${this.apiUser}/changePassword`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('access_token'));
    return this.http.post(url, { password }, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('access_token')}`
      })
    });
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(this.apiForgotPassword, { email }, this.apiConfig);
  }

  resetPassword(email: string, newPassword: string): Observable<any> {
    return this.http.post(this.apiResetPassword, { email, newPassword });
  }

  checkOTP(otp: string): Observable<any> {
    return this.http.post(this.apiCheckOTP, { otp });
  }

  getEmail(): string | null {
    return localStorage.getItem('email');
  }
  setEmail(otp: string): void {
    localStorage.setItem('email', otp);
  }

  removeEmail(): void {
    localStorage.removeItem('email');
  }

  getVipPeriod(token: string): Observable<any>{
    return this.http.get(this.apiVipPeriod, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      })
    })
  }

}