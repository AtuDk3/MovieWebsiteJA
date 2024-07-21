import { Component } from '@angular/core';
import { AdOption } from '../ad-popup-component/ad-popup-component.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {
  showAdPopup = false;
  selectedAdOption: AdOption | null = null;

  constructor(private http: HttpClient) {}

  openAdPopup(): void {
    this.showAdPopup = true;
  }

  onPopupClose(): void {
    this.showAdPopup = false;
  }

  onPopupSave(option: AdOption): void {
    this.selectedAdOption = option;
    this.showAdPopup = false;
    this.saveAdOption();
  }

  saveAdOption(): void {
    if (this.selectedAdOption) {
      console.log('Ad submitted', this.selectedAdOption);
      const mailtoLink = `mailto:webphimm@gmail.com?subject=Contact Us&body=Hello, I would like to contact you regarding an ad placement in the ${this.selectedAdOption.position} position which costs ${this.selectedAdOption.price}.`;
      window.location.href = mailtoLink;
    }
  }
  
}
