import { Component, OnInit} from '@angular/core';
import { GenreService } from '../../services/genre.service';
import { CountryService } from '../../services/country.service';
import { Genre } from '../../models/genre';
import { Country } from '../../models/country';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../services/token.service';
import { Router } from '@angular/router';
import { VipPeriodResponse } from '../../responses/user/vip_period.response';
import { BookmarkService } from '../../services/bookmark.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  genres: Genre[] = [];
  countries: Country[] = [];
  showGenreMenu: boolean = false;
  showCountryMenu: boolean = false;
  showYearMenu: boolean = false;
  showProfileMenu: boolean = false;
  userResponse?:UserResponse | null
  vipPeriodResponse?:VipPeriodResponse | null
  isPopoverOpen= false;
  search: string = '';
  bookmarkCount: number = 0;

  constructor(private genreService: GenreService, private countryService: CountryService,
    private userService: UserService,
    private tokenService: TokenService
    , private  router: Router, private bookmarkService: BookmarkService
  ) {    
  }

  ngOnInit() {
    this.getGenres();
    this.getCountries();
    if(!this.tokenService.isTokenExpired()){
      this.userResponse= this.userService.getUserResponseFromLocalStorage();
      if(this.userResponse?.user_vip.name.includes('vip') && this.userService.getVipPeriodResponseFromLocalStorage()===null){
        this.getVipPeriod();
      }   
      this.bookmarkService.currentBookmarkCount.subscribe(count =>{              
          this.bookmarkCount= count;              
      });    
    }else{
      this.userService.removeUserFromLocalStorage();
      this.userService.removeVipPeriodFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse= this.userService.getUserResponseFromLocalStorage();
    }
  }
  
  getGenres() {
    this.genreService.getGenres().subscribe({
      next: (response: any) => {
        this.genres = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getCountries() {
    this.countryService.getCountries().subscribe({
      next: (response: any) => {
        this.countries = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  } 

  showMenu(menu: string) {
    this.hideAllMenus();
    if (menu === 'genre') {
      this.showGenreMenu = true;
    } else if (menu === 'country') {
      this.showCountryMenu = true;
    } else if (menu === 'year') {
      this.showYearMenu = true;
    } else if (menu === 'profile') {
      this.showProfileMenu = true;
    }
  }

  hideMenu(menu: string) {
    if (menu === 'genre') {
      this.showGenreMenu = false;
    } else if (menu === 'country') {
      this.showCountryMenu = false;
    } else if (menu === 'year') {
      this.showYearMenu = false;
    } else if (menu === 'profile') {
      this.showProfileMenu = false;
    } 
  }

  private hideAllMenus() {
    this.showGenreMenu = false;
    this.showCountryMenu = false;
    this.showYearMenu = false;
    this.showProfileMenu = false;
  }

  searchMovie(){   
    this.router.navigate([''], { queryParams: {search: this.search } });
    this.search='';
  }

  logout(){
    this.userService.removeUserFromLocalStorage();
    this.userService.removeVipPeriodFromLocalStorage();
    this.tokenService.removeToken();
    this.userResponse= this.userService.getUserResponseFromLocalStorage();
    this.bookmarkService.resetBookmarkCount();
    this.router.navigate(['']);
  }

  getVipPeriod(){
    this.userService.getVipPeriod(this.tokenService.getToken()!).subscribe({
      next: (response: any) =>{
        debugger               
        this.vipPeriodResponse={
          ... response                                
       } 
        if (this.vipPeriodResponse) {                  
          const day =  ('0' + (new Date(this.vipPeriodResponse.registration_date).getDate())).slice(-2);
          const month = ('0' + (new Date(this.vipPeriodResponse.registration_date).getMonth() +1 )).slice(-2); 
          const year = new Date(this.vipPeriodResponse.registration_date).getFullYear();
          const formattedDate = `${day}/${month}/${year}`;
          this.vipPeriodResponse.registration_date_formatted = formattedDate;

          const day1 =  ('0' + (new Date(this.vipPeriodResponse.expiration_date).getDate())).slice(-2);
          const month1 = ('0' + (new Date(this.vipPeriodResponse.expiration_date).getMonth() +1 )).slice(-2); 
          const year1 = new Date(this.vipPeriodResponse.expiration_date).getFullYear();
          const formattedDate1 = `${day1}/${month1}/${year1}`;
          this.vipPeriodResponse.expiration_date_formatted = formattedDate1;
          this.userService.saveVipPeriodResponseToLocalStorage(this.vipPeriodResponse); 
      }             

      },
      complete: () => {
      },
      error: (error: any) => {               
        console.log(error.error.message);
      }
    })        
  }

  



  
}