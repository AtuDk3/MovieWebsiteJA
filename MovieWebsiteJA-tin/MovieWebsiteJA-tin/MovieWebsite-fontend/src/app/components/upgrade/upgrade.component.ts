import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserVip } from '../../models/user_vip';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-upgrade',
  templateUrl: './upgrade.component.html',
  styleUrl: './upgrade.component.scss'
})
export class UpgradeComponent implements OnInit{

  userVip: UserVip[] = []

  constructor(private router: Router, private userService: UserService) { }

  

  ngOnInit(): void {
    this.userService.getUserVip().subscribe({
      next: (response: any) => {  
        this.userVip= response;          
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  subscribe(amount: number, vip_name: string) {
    this.router.navigate(['/payment'], { 
      queryParams: { amount: amount, vip_name: vip_name } });
  }
}
