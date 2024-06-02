import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { UserResponse } from '../responses/user/user.response';
import { UpdateUserDTO } from '../dtos/user/updateuser.dto';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail= `${environment.apiBaseUrl}/users/details`;
  private apiUserImg= `${environment.apiBaseUrl}/users/images`;
  private apiUser= `${environment.apiBaseUrl}/users`;

  private apiConfig = {
    headers: this.createHeader(),
  }
  
  constructor(private http: HttpClient) { }

  private createHeader():HttpHeaders {
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

  getUserDetails(token: string){
    return this.http.post(this.apiUserDetail, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      })
    })
  }


  saveUserResponseToLocalStorage(userResponse?: UserResponse){
    try{
      if(userResponse==null || !userResponse){
        return;
      }
      const userResponseJSON= JSON.stringify(userResponse);
      localStorage.setItem('user', userResponseJSON);

      console.log('User response saved to local storage');
    }catch(error){
      console.error('Error saving user response to local storage:', error);
    }
  }

  getUserResponseFromLocalStorage():UserResponse | null{
    try{
      const userResponseJSON= localStorage.getItem('user');
      if(userResponseJSON == null || userResponseJSON == undefined){
        return null;
      }
      const userResponse= JSON.parse(userResponseJSON!);
      console.log('User response retrieved from local storage');
      return userResponse;
    }catch(error){
      console.log('Error retrieved user response from local storage:', error);
      return null;
    }
  }

  removeUserFromLocalStorage():void{
    try{
      localStorage.removeItem('user');
      console.log('User data removed from local storage')
    }catch(error){
      console.log(`Error removing user data from local storage:`, error);
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




}