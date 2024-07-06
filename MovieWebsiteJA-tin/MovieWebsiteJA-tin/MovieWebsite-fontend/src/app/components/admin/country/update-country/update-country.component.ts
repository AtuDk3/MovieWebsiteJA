import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CountryService } from '../../../../services/country.service';
import { Country } from '../../../../models/country';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-update-country',
  templateUrl: './update-country.component.html',
  styleUrl: './update-country.component.scss'
})
export class UpdateCountryComponent implements OnInit {
  country: Country | null = null;

  constructor(
    private route: ActivatedRoute,
    private countryService: CountryService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.countryService.getCountryById(id).subscribe({
        next: (response: Country) => {
          this.country = response;
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

  updateCountry() {
    if (this.country) {
      debugger
      this.countryService.updateCountry(this.country).subscribe({
        next: (response: any) => {
          this.toastr.success('The country was updated successfully!', 'Update Success', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
          this.router.navigate(['/admin/country/list-country']);
        },
        error: (error: any) => {
          console.log(error);
          this.toastr.error('There was a problem updating the country.', 'Update Failed', {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          });
        }
      });
    }
  }

}