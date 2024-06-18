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

  constructor(private http: HttpClient) { }

  getAllAds(page: number, limit: number): Observable<Ads[]> {
    return this.http.get<Ads[]>(this.apiGetAds);
  }

  getAdsById(adsId: number): Observable<any> {
    const url = `${this.apiGetAds}/${adsId}`;
    return this.http.get(url);
  }

  createAdse(ads: Ads): Observable<Ads> {
    return this.http.post<Ads>(this.apiGetAds, ads);
  }

  updateAds(adsDTO: AdsDTO): Observable<Ads> {
    const url = `${this.apiGetAds}/${adsDTO.id}`;
    return this.http.put<Ads>(url, adsDTO);
  }

  deleteAds(adsId: number): Observable<any> {
    const url = `${this.apiGetAds}/${adsId}`;
    return this.http.delete<any>(url);
  }

  getImage(imageName: string): Observable<Blob> {
    return this.http.get(`${this.apiGetAds}/${imageName}`, { responseType: 'blob' });
  }

  uploadImageAds(adsId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiGetAds}/upload_ads/${adsId}`, formData);
  }

  uploadImageFromCreateAds(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiGetAds}/upload_image_ads`, formData);
  }
}