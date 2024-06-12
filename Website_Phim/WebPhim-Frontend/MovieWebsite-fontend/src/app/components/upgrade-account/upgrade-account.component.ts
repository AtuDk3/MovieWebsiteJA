import { Component } from '@angular/core';

@Component({
  selector: 'app-upgrade-account',
  templateUrl: './upgrade-account.component.html',
  styleUrl: './upgrade-account.component.scss'
})
export class UpgradeAccountComponent {
  showUpgradePopup: boolean = false;

  openUpgradePopup() {
    this.showUpgradePopup = true;
  }

  closeUpgradePopup() {
    this.showUpgradePopup = false;
  }
}
