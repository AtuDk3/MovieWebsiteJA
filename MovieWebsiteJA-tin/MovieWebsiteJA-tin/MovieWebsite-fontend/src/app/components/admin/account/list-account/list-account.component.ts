import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';
import { AccountService } from '../../../../services/account.service';
import { UserResponse } from '../../../../responses/user/user.response';
import { UserService } from '../../../../services/user.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-list-account',
  templateUrl: './list-account.component.html',
  styleUrl: './list-account.component.scss'
})
export class ListAccountComponent implements OnInit {

  userResponse: UserResponse[] = []
  imageUrl: SafeUrl | string | null = null;

  constructor(private accountService: AccountService,
    private userService: UserService,
    private sanitizer: DomSanitizer,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.getListAccounts();
  }

  getListAccounts() {
    this.accountService.getListAccount().subscribe({
      next: (response: UserResponse[]) => {
        response.forEach((user: UserResponse) => {
          this.loadImage(user);
        });

        this.userResponse = response;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }



  loadImage(user: UserResponse) {
    this.userService.getImage(user.img_avatar).subscribe({
      next: (imageBlob: Blob) => {
        const objectURL = URL.createObjectURL(imageBlob);
        user.image_url = this.sanitizer.bypassSecurityTrustUrl(objectURL);
      },
      error: (error) => {
        console.error('Error loading image', error);
      },
      complete: () => {
        console.log('Image loading complete for user', user.id);
      }
    });
  }

  banAccount(user_id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will block this account!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, block it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.accountService.banAccount(user_id).subscribe({
          next: (response: any) => {                              
            this.toastr.success('Account blocked successfully!', 'Block Success', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            }); 
            this.getListAccounts();          
          },
          error: (error: any) => {
            this.toastr.error('There was a problem blocking the account.', 'Blocked Failed', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
            console.error('Ban account error:', error);
          }
        });
      }
    });
  }

  unBanAccount(user_id: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will unblock this account!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, unblock it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.accountService.unBanAccount(user_id).subscribe({
          next: (response: any) => {                              
            this.toastr.success('Account unblocked successfully!', 'Unblock Success', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            }); 
            this.getListAccounts();           
          },
          error: (error: any) => {
            this.toastr.error('There was a problem unblocking the account.', 'Unblocked Failed', {
              timeOut: 3000,
              positionClass: 'toast-bottom-right'
            });
            console.error('UnBan account error:', error);
          }
        });
      }
    });
  }



}