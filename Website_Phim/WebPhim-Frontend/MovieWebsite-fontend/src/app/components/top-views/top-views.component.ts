
import { Component, OnInit} from '@angular/core';
import { TopViewService } from '../../services/top_view.service';
import { TopViewResponse } from '../../responses/user/top_view.response';
import { environment } from '../../environments/environment';
@Component({
  selector: 'app-top-views',
  templateUrl: './top-views.component.html',
  styleUrl: './top-views.component.scss'
})
export class TopViewsComponent implements OnInit {

  topViewResponse: TopViewResponse[] =[]
  statusDay: string = ''
  statusWeek: string = ''
  statusMonth: string = ''

  constructor(
    private topViewService: TopViewService) { }

    ngOnInit() {
      this.getTopViewDay();    
      this.updateViewForDay();   
    }

    getTopViewDay() {
      this.topViewService.getTopViewByDay().subscribe({
        next: (response: any) => {
          response.forEach((movie_view: TopViewResponse) => {
            if(!movie_view.image.includes('http')){
              movie_view.url = `${environment.apiBaseUrl}/movies/images/${movie_view.image}`;
            }else{
              movie_view.url = movie_view.image;
            }        
          });
          this.statusDay= 'active'
          this.statusWeek= ''
          this.statusMonth= ''
          this.topViewResponse = response;        
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }

    updateViewForDay() {
      this.topViewService.updateViewForDay().subscribe({
        next: (response: any) => {                      
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }

    getTopViewWeek() {
      this.topViewService.getTopViewByWeek().subscribe({
        next: (response: any) => {
          response.forEach((movie_view: TopViewResponse) => {
            if(!movie_view.image.includes('http')){
              movie_view.url = `${environment.apiBaseUrl}/movies/images/${movie_view.image}`;
            }else{
              movie_view.url = movie_view.image;
            }
          });
          this.statusWeek= 'active'
          this.statusDay= ''
          this.statusMonth= ''
          this.topViewResponse = response;        
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }

    getTopViewMonth() {
      this.topViewService.getTopViewByMonth().subscribe({
        next: (response: any) => {
          response.forEach((movie_view: TopViewResponse) => {
            if(!movie_view.image.includes('http')){
              movie_view.url = `${environment.apiBaseUrl}/movies/images/${movie_view.image}`;
            }else{
              movie_view.url = movie_view.image;
            }
          });
          this.statusMonth= 'active'
          this.statusDay= ''
          this.statusWeek= ''
          this.topViewResponse = response;        
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }

}