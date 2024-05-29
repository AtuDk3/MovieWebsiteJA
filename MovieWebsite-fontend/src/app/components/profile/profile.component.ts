import { Component } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  showUpdateForm: boolean = false;
  changePasswordForm: boolean = false;

  toggleUpdateForm() {
    this.showUpdateForm = !this.showUpdateForm;
  }

  togglechangePassword() {
    this.changePasswordForm = !this.changePasswordForm;
  }
}
