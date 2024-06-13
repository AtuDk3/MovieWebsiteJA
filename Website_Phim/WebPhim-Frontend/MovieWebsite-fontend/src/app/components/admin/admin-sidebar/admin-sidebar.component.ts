
import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-sidebar',
  templateUrl: './admin-sidebar.component.html',
  styleUrl: './admin-sidebar.component.scss'
})
export class AdminSidebarComponent {
  dropdownStates: { [key: string]: boolean } = {
    genre: false,
    country: false,
    movie_type: false,
    movie: false,
    episode: false,
    order: false,
    account: false,
    storage: false
  };

  openDropdown(menu: string): void {
    this.dropdownStates[menu] = true;
  }

  closeDropdown(menu: string): void {
    this.dropdownStates[menu] = false;
  }

  isDropdownOpen(menu: string): boolean {
    return this.dropdownStates[menu];
  }
}
