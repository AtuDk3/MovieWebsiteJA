import { Component, OnInit} from '@angular/core';
import { GenreService } from '../../service/genre.service';
import { CountryService } from '../../service/country.service';
import { Genre } from '../../models/genre';
import { Country } from '../../models/country';
import { UserService } from '../../service/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../service/token.service';
import { environment } from '../../environments/environment';
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
  isPopoverOpen= false;

  constructor(private genreService: GenreService, private countryService: CountryService,
    private userService: UserService,
    private tokenService: TokenService
  ) {
    
  }

  ngOnInit() {
    this.getGenres();
    this.getCountries();
    this.userResponse= this.userService.getUserResponseFromLocalStorage();
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

  // togglePopover(event: Event): void{
  //   event.preventDefault();
  //   this.isPopoverOpen= !this.isPopoverOpen;
  // }

  // handleItemClick(index: number): void{
  //   //alert(`Clicked on "${index}"`);
  //   if(index==1){
  //     this.userService.removeUserFromLocalStorage();
  //     this.tokenService.removeToken();
  //     this.userResponse= this.userService.getUserResponseFromLocalStorage();
  //   }
  //   this.isPopoverOpen= false;
  // }



  
}