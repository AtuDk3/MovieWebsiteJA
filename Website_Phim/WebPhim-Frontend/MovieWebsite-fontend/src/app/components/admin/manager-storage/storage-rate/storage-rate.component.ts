
import { Component, OnInit } from '@angular/core';
import { RateService } from '../../../../services/rate.service'
import { ManagerStorage } from '../../../../models/storage'; 

@Component({
  selector: 'app-storage-rate',
  templateUrl: './storage-rate.component.html',
  styleUrl: './storage-rate.component.scss'
})
export class StorageRateComponent implements OnInit{
  
  storage: ManagerStorage | null =null

 constructor(private rateService: RateService) {}

 ngOnInit() {
   this.getLastDeleteRate();
 }
  getLastDeleteRate(){
    this.rateService.getGetLastDeleteRate().subscribe({
      next: (response: any) => {
        
        const day = ('0' + new Date(response.lastDelete).getDate()).slice(-2);
            this.storage = response;
            if(this.storage){
              const month = ('0' + (new Date(response.lastDelete).getMonth() + 1)).slice(-2);
              const year = new Date(response.lastDelete).getFullYear();
              const formattedDate = `${day}/${month}/${year}`;
              this.storage.lastDelete_formatDate = formattedDate;     
          }             
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  deleteRate(){
    this.rateService.deleteOldRateMonth().subscribe({
      next: (response: any) => {
        this.getLastDeleteRate();     
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
}
