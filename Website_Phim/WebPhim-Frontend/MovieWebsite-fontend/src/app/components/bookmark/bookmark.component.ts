import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { FavouriteResponse } from '../../responses/user/favourite.response';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../services/token.service';
import { BookmarkService } from '../../services/bookmark.service';

@Component({
  selector: 'app-bookmark',
  templateUrl: './bookmark.component.html',
  styleUrl: './bookmark.component.scss'
})
export class BookmarkComponent implements OnInit {

  favouriteResponse: FavouriteResponse[] = [];  
  userResponse: UserResponse | null = null;
  
  isData: boolean= false;

  constructor(private userService: UserService,
    private tokenService: TokenService,
    private bookmarkServive: BookmarkService,
  ) {
  }

  ngOnInit() {
    this.userResponse= this.userService.getUserResponseFromLocalStorage();
    this.load();
  }
  load(){   
    if(this.userResponse){    
        
        this.bookmarkServive.getMovieFavourite(this.userResponse.id, this.tokenService.getToken()!).subscribe({
          next: (response: any) => {
            console.log(response)
            if(response && response.length > 0){  
              this.isData= true;          
              response.forEach((favourite: FavouriteResponse) => {              
              favourite.url = `${environment.apiBaseUrl}/movies/images/${favourite.image}`;
              this.favouriteResponse = response;
            });           
           
          }else{
            this.isData= false; 
          }
          },
          error: (error: any) => {
            console.log(error);
          }
        
        });
        
      }  
  }

  deleteFavourite(movie_id: number){
    if(this.userResponse){
    this.bookmarkServive.deleteMovieFavourite(this.userResponse.id,movie_id, this.tokenService.getToken()!).subscribe(
      {
        next: (response: any) => {
         this.bookmarkServive.subtractBookmarkCount();
         this.load();
          //window.location.reload(); 
        },
        complete: () => {
        },
        error: (err) => {
          console.log('error delete favourite');
        }
      });
    }
  }



}
