import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-upgrade',
  templateUrl: './upgrade.component.html',
  styleUrl: './upgrade.component.scss'
})
export class UpgradeComponent {
  constructor(private router: Router) { }

  subscribe(amount: number) {
    this.router.navigate(['/payment'], { queryParams: { amount: amount } });
  }
}
