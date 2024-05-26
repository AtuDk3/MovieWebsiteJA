import { Component, OnInit} from '@angular/core';
import { GenreService } from '../../service/genre.service';
import { CountryService } from '../../service/country.service';
import { Genre } from '../../models/genre';
import { Country } from '../../models/country';

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

  constructor(private genreService: GenreService, private countryService: CountryService) {
  }

  ngOnInit() {
    this.getGenres();
    this.getCountries();
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
    }
  }

  hideMenu(menu: string) {
    if (menu === 'genre') {
      this.showGenreMenu = false;
    } else if (menu === 'country') {
      this.showCountryMenu = false;
    } else if (menu === 'year') {
      this.showYearMenu = false;
    }
  }

  private hideAllMenus() {
    this.showGenreMenu = false;
    this.showCountryMenu = false;
    this.showYearMenu = false;
  }

  
}
