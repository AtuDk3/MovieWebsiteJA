import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-layout',
  template: `
  <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
  <app-admin-sidebar></app-admin-sidebar>
  <app-admin-header></app-admin-header>
  <router-outlet></router-outlet>
`,
  styleUrl: './admin-layout.component.scss'
})
export class AdminLayoutComponent {
}
