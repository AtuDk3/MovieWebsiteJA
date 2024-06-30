import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { Observable } from 'rxjs';
import { OrderResponse } from '../../../responses/user/order.response';
import { OrderService } from '../../../services/order.service';
Chart.register(...registerables);

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {

  progressData = [
    { value: 1500, label: 'Traffic', percentage: '40%' },
    { value: 234, label: 'Sales', percentage: '60%' },
    { value: 465, label: 'Pageviews', percentage: '30%' },
    { value: 235, label: 'Visitors', percentage: '80%' },
  ];

  chart!: Chart;
  labeldata: string[] = [];
  realdata: number[] = [];
  colordata: string[] = [];

  years: number[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.getYears();
  }

  getYears() {
    this.orderService.getYears().subscribe({
      next: (years: number[]) => {
        this.years = years;
        if (this.years.length > 0) {
          this.getOrderByMonth(this.years[0]);
        }
      },
      error: (error: any) => {
        console.error('Error fetching years!', error);
      }
    });
  }

  RenderChart(labels: string[], data: number[], colors: string[], type: any, id: any) {
    if (this.chart) {
      this.chart.destroy(); // Destroy previous chart instance
    }

    this.chart = new Chart(id, {
      type: type,
      data: {
        labels: labels,
        datasets: [{
          label: '# Số Tiền',
          data: data,
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(255, 205, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(201, 203, 207, 1)'
          ],
          backgroundColor: colors.map(color => color.replace('0.2', '0.5') as string), // Explicitly cast to string
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  onYearChange(event: Event) {
    const selectedYear = (event.target as HTMLSelectElement).value;
    const year = parseInt(selectedYear, 10);
    this.getOrderByMonth(year); // Fetch data for the selected year
  }

  getOrderByMonth(year: number) {
    this.orderService.getOrderByMonth(year).subscribe({
      next: (response: any) => {
        const totalPriceByMonth = response.totalPriceByMonth;
        this.labeldata = totalPriceByMonth.map((item: any) => `Tháng ${item.month}`);
        this.realdata = totalPriceByMonth.map((item: any) => item.totalPrice);
        this.colordata = this.generateColors(this.realdata.length);
        this.RenderChart(this.labeldata, this.realdata, this.colordata, 'bar', 'barchart');
      },
      error: (error: any) => {
        console.error('Error fetching order by month data!', error);
      }
    });
  }

  generateColors(length: number): string[] {
    const baseColors = [
      'rgba(255, 99, 132, 0.2)',
      'rgba(255, 159, 64, 0.2)',
      'rgba(255, 205, 86, 0.2)',
      'rgba(75, 192, 192, 0.2)',
      'rgba(54, 162, 235, 0.2)',
      'rgba(153, 102, 255, 0.2)',
      'rgba(201, 203, 207, 0.2)'
    ];
    const colors: string[] = [];
    for (let i = 0; i < length; i++) {
      colors.push(baseColors[i % baseColors.length]);
    }
    return colors;
  }
}
