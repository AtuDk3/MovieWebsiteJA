
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ads } from '../models/ads';
import { environment } from '../environments/environment';
import { AdsDTO } from '../dtos/user/ads.dto';

@Injectable({
  providedIn: 'root'
})
export class AdsService {

  private apiGetAds = `${environment.apiBaseUrl}/ads`;
  private apiGetAdsAdmin = `${environment.apiBaseUrl}/ads/ads_admin`;
  private apiUpdateAdsPayment = `${environment.apiBaseUrl}/ads/update_ads_payment`;
  private apiSendTradingCode = `${environment.apiBaseUrl}/ads/send_trading_code`;
  private apiCheckTradingCode = `${environment.apiBaseUrl}/ads/check_trading_code`;

  constructor(private http: HttpClient) { }

  getAllAds(page: number, limit: number): Observable<Ads[]> {
    const url = `${this.apiGetAds}/get_ads`;
    return this.http.get<Ads[]>(url);
  }

  getAllAdsAdmin(page: number, limit: number): Observable<Ads[]> {
    return this.http.get<Ads[]>(this.apiGetAdsAdmin);
  }

  getAdsById(adsId: number): Observable<any> {
    const url = `${this.apiGetAds}/${adsId}`;
    return this.http.get(url);
  }

  createAds(ads: AdsDTO): Observable<Ads> {
    return this.http.post<Ads>(this.apiGetAds, ads);
  }

  updateAdsPayment(trading_code:string): Observable<Ads> {
    return this.http.put<Ads>(this.apiUpdateAdsPayment, {trading_code});
  }

  sendTradingCode(trading_code:string, email: string): Observable<any> {
    return this.http.post(this.apiSendTradingCode, { trading_code, email });
  }

  checkTradingCode(trading_code:string): Observable<any> {
    return this.http.post(this.apiCheckTradingCode, { trading_code});
  }

  deleteAds(adsId: number): Observable<any> {
    const url = `${this.apiGetAds}/${adsId}`;
    return this.http.delete<any>(url);
  }

  getImage(imageName: string): Observable<Blob> {
    return this.http.get(`${this.apiGetAds}/images/${imageName}`, { responseType: 'blob' });
  }

  uploadImageAds(adsId: number, files: File[]): Observable<any> {
    const formData = new FormData();
    files.forEach(file => {
      formData.append('files', file); // Use 'files' as key to match with @ModelAttribute("files")
    });
    return this.http.post(`${this.apiGetAds}/upload_ads/${adsId}`, formData);
  }

  uploadImagesFromCreateAds(files: File[]): Observable<any> {
    const formData = new FormData();
    files.forEach(file => {
      formData.append('files', file); // Use 'files' as key to match with @ModelAttribute("files")
    });
    return this.http.post(`${this.apiGetAds}/upload_image_ads`, formData);
  }

  updateAds(ads_id: number, ads : Ads){
    const url = `${this.apiGetAds}/${ads_id}`;
    return this.http.put(url, ads);
  }

}