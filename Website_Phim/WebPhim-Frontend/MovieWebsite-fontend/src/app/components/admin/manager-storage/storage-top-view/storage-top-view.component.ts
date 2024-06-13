import { Component, OnInit } from '@angular/core';
import { TopViewService } from '../../../../services/top_view.service';
import { ManagerStorage } from '../../../../models/storage';
@Component({
  selector: 'app-storage-top-view',
  templateUrl: './storage-top-view.component.html',
  styleUrl: './storage-top-view.component.scss'
})
export class StorageTopViewComponent implements OnInit{

  storage: ManagerStorage | null =null

 constructor(private topViewService: TopViewService) {}

 ngOnInit() {
   this.getLastDeleteView();
 }
  getLastDeleteView(){
    this.topViewService.getGetLastDeleteView().subscribe({
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

  deleteView(){
    this.topViewService.deleteOldViewMonth().subscribe({
      next: (response: any) => {
        this.getLastDeleteView();     
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
}
