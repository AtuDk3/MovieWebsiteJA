import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface AdOption {
  position: string;
  price: number;
}

@Component({
  selector: 'app-ad-popup-component',
  templateUrl: './ad-popup-component.component.html',
  styleUrl: './ad-popup-component.component.scss'
})
export class AdPopupComponentComponent {
  @Input() showPopup = false;
  @Output() closePopup = new EventEmitter<void>();
  @Output() savePopup = new EventEmitter<AdOption>();

  adOptions: AdOption[] = [
    { position: 'Header', price: 1000 },
    { position: 'Sidebar', price: 500 },
    { position: 'Footer', price: 300 }
  ];

  selectedOption: AdOption | null = null;

  close(): void {
    this.closePopup.emit();
  }

  save(): void {
    if (this.selectedOption) {
      this.savePopup.emit(this.selectedOption);
    } else {
      alert('Vui lòng chọn một tùy chọn.');
    }
  }
}
