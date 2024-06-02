import { Component } from '@angular/core';

@Component({
  selector: 'app-main-layout',
  template: `
    <app-header></app-header>
    <router-outlet></router-outlet>
    <app-footer></app-footer>
  `,
  styleUrls: ['./main-layout.component.scss']
})
export class MainLayoutComponent {}
