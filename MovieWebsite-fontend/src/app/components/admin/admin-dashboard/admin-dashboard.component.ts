import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../../services/order.service';


@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit{
  chartData: any[] = [];
  chartLabels: string[] = [];
  chartOptions = {
    responsive: true,
  };
  chartLegend = true;
  chartType: string = 'bar';

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.orderService.getOrderStatistics().subscribe(data => {
      this.chartData = [{ data: data.orders, label: 'Orders' }];
      this.chartLabels = data.labels;
    });
  }
}
