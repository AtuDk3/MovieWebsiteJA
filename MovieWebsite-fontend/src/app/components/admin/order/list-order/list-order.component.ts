import { Component, OnInit } from '@angular/core';
import { OrderResponse } from '../../../../responses/user/order.response';
import { OrderService } from '../../../../services/order.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-order',
  templateUrl: './list-order.component.html',
  styleUrl: './list-order.component.scss'
})
export class ListOrderComponent implements OnInit {

  orderResponse: OrderResponse[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 15;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];

  constructor(private orderService: OrderService,
              private router: Router
  ) { }

  ngOnInit() {
    this.getOrders(this.currentPage, this.itemsPerPage);
  }

  getOrders(page: number, limit: number) {
    this.orderService.getOrders(page, limit).subscribe({
      next: (response: any) => {
        response.orders.forEach((order: OrderResponse) => {   
        });
        this.orderResponse = response.orders;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      error: (error: any) => {
        console.error('Error fetching get all orders!', error);
      }
    });
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const maxVisiblePages = 5;
    const halfVisiblePages = Math.floor(maxVisiblePages / 2);

    let startPage = Math.max(currentPage - halfVisiblePages, 0); // Bắt đầu từ 0
    let endPage = Math.min(startPage + maxVisiblePages - 1, totalPages - 1); // Dựa vào totalPages

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(endPage - maxVisiblePages + 1, 0); // Bắt đầu từ 0
    }

    return new Array(endPage - startPage + 1).fill(0).map((_, index) => startPage + index);
  }

  goToPage(page: number, event: Event) {
    event.preventDefault();
    if (page >= 0 && page <= this.totalPages - 1) { // Đảm bảo không vượt quá giới hạn trang
      this.currentPage = page;
      this.getOrders(this.currentPage, this.itemsPerPage);
    }
  }

  historyOrder(user_id: number){
    this.router.navigate([`admin/order/list-history-order/${user_id}`]);
  }
}