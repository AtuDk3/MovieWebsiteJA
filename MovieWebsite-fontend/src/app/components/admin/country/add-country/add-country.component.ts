import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Country } from '../../../../models/country';
import { CountryService } from '../../../../services/country.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-country',
  templateUrl: './add-country.component.html',
  styleUrl: './add-country.component.scss'
})
export class AddCountryComponent {
  country: Country = {
    id: 0,
    name: '',
    isActive: 1
  };

  constructor(private countryService: CountryService, 
    private router: Router, 
    private toastr: ToastrService) {}

  addCountry() {
    this.countryService.createCountry(this.country).subscribe({
      next: (response: any) => {
        this.toastr.success('The country was added successfully!', 'Add Success', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
        this.router.navigate(['admin/country/list-country']);
      },
      error: (error: any) => {
        console.error('Failed to add country:', error);
        this.toastr.error('There was a problem adding the country.', 'Add Failed', {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    });
  }
}
