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

  constructor(
    private topViewService: TopViewService) { }

    ngOnInit() {
      this.getTopViewDay();
    }

    getTopViewDay() {
      this.topViewService.getTopViewByDay().subscribe({
        next: (response: any) => {
          response.forEach((movie_view: TopViewResponse) => {
            movie_view.url = `${environment.apiBaseUrl}/movies/images/${movie_view.image}`;
          });
          debugger
          this.topViewResponse = response;        
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }

}
