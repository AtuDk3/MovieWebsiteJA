import { Component, HostListener  } from '@angular/core';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrl: './admin-header.component.scss'
})
export class AdminHeaderComponent {
  sidebarActive: boolean = false;

  @HostListener('mouseenter')
  onMouseEnter() {
    this.sidebarActive = true;
  }

  @HostListener('mouseleave')
  onMouseLeave() {
    this.sidebarActive = false;
  }
}
