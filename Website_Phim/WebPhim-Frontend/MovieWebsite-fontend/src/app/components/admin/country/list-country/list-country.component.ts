import { Component, OnInit} from '@angular/core';
import { CountryService } from '../../../../services/country.service';
import { Country } from '../../../../models/country';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list-country',
  templateUrl: './list-country.component.html',
  styleUrl: './list-country.component.scss'
})
export class ListCountryComponent implements OnInit {
  countries: Country[] = [];
  message: string | null = null;

  constructor(private countryService: CountryService,
    private  router: Router,
    private toastr: ToastrService) {
   const navigation = this.router.getCurrentNavigation();
   if (navigation && navigation.extras.state && navigation.extras.state['message']) {
     this.message = navigation.extras.state['message'];
   }
 }

 ngOnInit() {
   this.getListCountries();
 }

 getListCountries() {
   this.countryService.getCountries().subscribe({
     next: (response: any) => {
       this.countries = response;
       console.log(this.countries);
     },
     error: (error: any) => {
       console.log(error);
     }
   });
 }

 updateCountry(country: Country) {
   this.router.navigate(['admin/country/update-country', country.id]);
 }

 deleteCountry(id: number) {
   Swal.fire({
     title: 'Are you sure?',
     text: 'You will not be able to recover this country!',
     icon: 'warning',
     showCancelButton: true,
     confirmButtonColor: '#3085d6',
     cancelButtonColor: '#d33',
     confirmButtonText: 'Yes, delete it!',
   }).then((result) => {
     if (result.isConfirmed) {
       this.countryService.deleteCountry(id).subscribe({
         next: () => {
           this.toastr.success('Country deleted successfully!', 'Delete Success', {
             timeOut: 3000,
             positionClass: 'toast-bottom-right'
           });
           this.getListCountries();
         },
         error: (error: any) => {
           console.log(error);
           this.toastr.error('There was a problem deleting the country.', 'Delete Failed', {
             timeOut: 3000,
             positionClass: 'toast-bottom-right'
           });
         }
       });
     }
   });
 }
}
