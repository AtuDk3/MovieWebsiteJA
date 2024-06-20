
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';
@Injectable({
  providedIn: 'root'
})
export class AuthFBService {
    private clientId = '778799574415865'; // Thay YOUR_FACEBOOK_CLIENT_ID bằng clientId của ứng dụng Facebook của bạn
    private clientSecret = '4654e032fccf722d50b699ea4929261d'; // Thay YOUR_FACEBOOK_CLIENT_SECRET bằng clientSecret của ứng dụng Facebook của bạn
    private redirectUri = 'http://localhost:4200/'; // Thay 'https://your-angular-app-url/login-callback' bằng URI callback của ứng dụng Angular của bạn
  
    constructor(private http: HttpClient) { }

  // Phương thức để chuyển hướng người dùng đến trang đăng nhập Facebook
  login() {
    window.location.href = `https://www.facebook.com/v12.0/dialog/oauth?client_id=${this.clientId}&redirect_uri=${encodeURIComponent(this.redirectUri)}&scope=public_profile`;
    // Thay 'public_profile,email' bằng các phạm vi mà bạn muốn yêu cầu từ người dùng
  }

  // Xử lý callback sau khi người dùng đăng nhập thành công
  handleLoginCallback(): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      // Lấy mã truy cập từ URL fragment
      const code = new URLSearchParams(window.location.hash.substring(1)).get('code');
      if (code) {
        // Gửi yêu cầu lấy accessToken từ Facebook
        const url = `https://graph.facebook.com/v12.0/oauth/access_token?client_id=${this.clientId}&redirect_uri=${encodeURIComponent(this.redirectUri)}&client_secret=${encodeURIComponent(this.clientSecret)}&code=${code}`;
        
        this.http.get(url)
          .toPromise()
          .then((response: any) => {
            const accessToken = response.access_token;
            // Lưu trữ accessToken vào localStorage hoặc cách khác phù hợp
            localStorage.setItem('fbAccessToken', accessToken);
            resolve();
          })
          .catch(error => {
            reject(error);
          });
      } else {
        reject('Code not found');
      }
    });
  }

  // Lấy thông tin người dùng từ Facebook sử dụng accessToken
  getUserProfile(): Promise<any> {
    const accessToken = localStorage.getItem('fbAccessToken');
    if (!accessToken) {
      return Promise.reject('Access token not found');
    }
  
    return this.http.get(`https://graph.facebook.com/me?fields=id,name,email&access_token=${accessToken}`)
      .toPromise();
  }

  // Đăng xuất
  logout() {
    localStorage.removeItem('fbAccessToken');
    // Có thể gọi API để đăng xuất khỏi Facebook nếu cần
  }

}