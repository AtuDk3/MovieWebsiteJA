import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  chartdata: any;
  labeldata: string[] = [];
  realdata: number[] = [];
  colordata: string[] = [];

  progressData = [
    { value: 1500, label: 'Traffic', percentage: '40%' },
    { value: 234, label: 'Sales', percentage: '60%' },
    { value: 465, label: 'Pageviews', percentage: '30%' },
    { value: 235, label: 'Visitors', percentage: '80%' },
  ];

  constructor() { }

  ngOnInit(): void {
    // Gán dữ liệu cố định
    this.labeldata = ['2016', '2017', '2018', '2019', '2020', '2021', '2022'];
    this.realdata = [65, 59, 80, 81, 56, 55, 40];
    this.colordata = [
      'rgba(255, 99, 132, 0.2)',
      'rgba(255, 159, 64, 0.2)',
      'rgba(255, 205, 86, 0.2)',
      'rgba(75, 192, 192, 0.2)',
      'rgba(54, 162, 235, 0.2)',
      'rgba(153, 102, 255, 0.2)',
      'rgba(201, 203, 207, 0.2)'
    ];

    this.RenderChart(this.labeldata,this.realdata,this.colordata,'bar','barchart');
  }

  RenderChart(labeldata: any, maindata: any, colordata: any, type: any, id: any) {
    const myChart = new Chart(id, {
      type: type,
      data: {
        labels: labeldata,
        datasets: [{
          label: '# of Votes',
          data: maindata,
          backgroundColor: colordata,
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(255, 205, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(201, 203, 207, 1)'
          ],
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
}
