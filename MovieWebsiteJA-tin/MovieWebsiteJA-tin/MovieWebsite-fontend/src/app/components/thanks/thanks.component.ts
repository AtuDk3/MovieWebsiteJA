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