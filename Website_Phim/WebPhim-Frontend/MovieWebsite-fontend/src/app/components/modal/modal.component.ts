import { Component } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {
  isClosed = false;

  closeModal() {
    this.isClosed = true;
  }

  subscribe(event: Event) {
    event.preventDefault();
    console.log('Subscribed');
    this.closeModal();
  }
}
