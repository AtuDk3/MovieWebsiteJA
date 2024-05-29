import { Component, OnInit, AfterViewInit } from '@angular/core';
import 'owl.carousel';

@Component({
  selector: 'app-movie-related',
  templateUrl: './movie-related.component.html',
  styleUrl: './movie-related.component.scss'
})
export class MovieRelatedComponent implements OnInit, AfterViewInit {
  constructor() { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.initOwlCarousel();
  }

  initOwlCarousel(): void {
    $('#halim_related_movies-2').owlCarousel({
      loop: true,
      margin: 4,
      autoplay: true,
      autoplayTimeout: 4000,
      autoplayHoverPause: true,
      nav: true,
      navText: ['<i class="fa-solid fa-angles-left"></i>', '<i class="fa-solid fa-angles-right"></i>'],
      responsiveClass: true,
      responsive: {
        0: { items: 2 },
        480: { items: 3 },
        600: { items: 4 },
        1000: { items: 4 }
      }
    });
  }

}
