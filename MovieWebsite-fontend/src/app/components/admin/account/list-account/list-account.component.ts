import { Component, OnInit} from '@angular/core';
import { MovieService } from '../../../../services/movie.service';
import { Movie } from '../../../../models/movie';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-list-account',
  templateUrl: './list-account.component.html',
  styleUrl: './list-account.component.scss'
})
export class ListAccountComponent {

}
