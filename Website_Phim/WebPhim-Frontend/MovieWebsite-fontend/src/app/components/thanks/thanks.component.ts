import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { VnpayService } from '../../services/vnpay.service';
import { UserService } from '../../services/user.service';
import { UpdateUserDTO } from '../../dtos/user/updateuser.dto';
import { UserResponse } from '../../responses/user/user.response';
import { TokenService } from '../../services/token.service';
import { VipPeriodResponse } from '../../responses/user/vip_period.response';
import { OrderDTO } from '../../dtos/user/order.dto';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-thanks',
  templateUrl: './thanks.component.html',
  styleUrls: ['./thanks.component.scss']
})
export class ThanksComponent implements OnInit {
  vnp_ammount: number = 0
  vip_name: string = ''
  userResponse?: UserResponse | null
  vipPeriodResponse?: VipPeriodResponse | null

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private vnpayService: VnpayService,
    private userService: UserService,
    private tokenService: TokenService,
    private orderService: OrderService
  ) { }

  // ngOnInit(): void {
  //   this.route.queryParams.subscribe(params => {

  //     this.vnpayService.paymentCompleted().subscribe({
  //       next: (response: string) => {
  //         if (response.startsWith('redirect:')) {
  //           const redirectUrl = response.substring('redirect:'.length);
  //           window.location.href = redirectUrl;
  //         } else {
  //           console.error('Invalid response:', response);
  //         }
  //       },
  //       error: (error) => {
  //         console.error('Payment request failed', error);
  //       }
  //     });
  //     });
  //   };
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.vnp_ammount = Number(params['vnp_Amount']) / 100;
      this.vip_name = (params['vnp_OrderInfo']);
      const updateUserDTO: UpdateUserDTO = {
        full_name: '',
        vip_name: this.vip_name
      };
      this.userResponse = this.userService.getUserResponseFromLocalStorage();
      if (this.userResponse) {
        this.userService.updateUserDetails(this.userResponse.id, updateUserDTO, this.tokenService.getToken()!)
          .subscribe({
            next: (response: any) => {
              this.userResponse = {
                ...response,
                date_of_birth: new Date(response.date_of_birth),
                created_at: new Date(response.created_at)
              };
              if (this.userResponse) {
                const day = ('0' + new Date(this.userResponse.created_at).getDate()).slice(-2);
                const month = ('0' + (new Date(this.userResponse.created_at).getMonth() + 1)).slice(-2);
                const year = new Date(this.userResponse.created_at).getFullYear();
                const formattedDate = `${day}/${month}/${year}`;
                this.userResponse.created_at_formatted = formattedDate;

                const day_of_birth = ('0' + new Date(this.userResponse.date_of_birth).getDate()).slice(-2);
                const month_of_birth = ('0' + (new Date(this.userResponse.date_of_birth).getMonth() + 1)).slice(-2);
                const year_of_birth = new Date(this.userResponse.date_of_birth).getFullYear();
                const formatted_of_birth = `${day_of_birth}/${month_of_birth}/${year_of_birth}`;
                this.userResponse.date_of_birth_formatted = formatted_of_birth;
                this.userService.removeUserFromLocalStorage();
                this.userService.removeVipPeriodFromLocalStorage();
                this.userService.saveUserResponseToLocalStorage(this.userResponse);

                this.userResponse = this.userService.getUserResponseFromLocalStorage();
                if (this.userResponse) {

                  this.userService.createVipPeriod(this.userResponse.id, this.tokenService.getToken()!).subscribe(
                    {
                      next: (response: any) => {
                        this.getVipPeriod();
                      },
                      complete: () => {
                      },
                      error: (err) => {
                        console.log('error');
                      }
                    });

                  const orderDTO: OrderDTO = {
                    user_id: this.userResponse.id,
                    price: this.vnp_ammount
                  };
                  this.orderService.createOrder(orderDTO, this.tokenService.getToken()!).subscribe(
                    {
                      next: (response: any) => {
                      },
                      complete: () => {
                      },
                      error: (err) => {
                        console.log('error create order');
                      }
                    });
                }
              }

            },
            error: (error: any) => {
              console.error('Error create vip period', error);

            },
            complete: () => {
            }
          });
      }
    });

  }

  getVipPeriod() {
    this.userService.getVipPeriod(this.tokenService.getToken()!).subscribe({
      next: (response: any) => {
        debugger
        this.vipPeriodResponse = {
          ...response
        }
        if (this.vipPeriodResponse) {
          const day = ('0' + (new Date(this.vipPeriodResponse.registration_date).getDate())).slice(-2);
          const month = ('0' + (new Date(this.vipPeriodResponse.registration_date).getMonth() + 1)).slice(-2);
          const year = new Date(this.vipPeriodResponse.registration_date).getFullYear();
          const formattedDate = `${day}/${month}/${year}`;
          this.vipPeriodResponse.registration_date_formatted = formattedDate;

          const day1 = ('0' + (new Date(this.vipPeriodResponse.expiration_date).getDate())).slice(-2);
          const month1 = ('0' + (new Date(this.vipPeriodResponse.expiration_date).getMonth() + 1)).slice(-2);
          const year1 = new Date(this.vipPeriodResponse.expiration_date).getFullYear();
          const formattedDate1 = `${day1}/${month1}/${year1}`;
          this.vipPeriodResponse.expiration_date_formatted = formattedDate1;
          this.userService.saveVipPeriodResponseToLocalStorage(this.vipPeriodResponse);
          this.vipPeriodResponse = this.userService.getVipPeriodResponseFromLocalStorage();
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
