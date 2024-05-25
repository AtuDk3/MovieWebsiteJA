// sidebar.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  isOpen = false;

  categories = [
    {
      name: 'Clothes',
      icon: 'assets/images/icons/dress.svg',
      isOpen: false,
      items: [
        { name: 'Shirt', stock: 300 },
        { name: 'shorts & jeans', stock: 60 },
        { name: 'jacket', stock: 50 },
        { name: 'dress & frock', stock: 87 }
      ]
    },
    {
      name: 'Footwear',
      icon: 'assets/images/icons/shoes.svg',
      isOpen: false,
      items: [
        { name: 'Sports', stock: 45 },
        { name: 'Formal', stock: 75 },
        { name: 'Casual', stock: 35 },
        { name: 'Safety Shoes', stock: 26 }
      ]
    },
    // Add other categories in a similar manner
  ];

  toggleAccordion(index: number) {
    this.categories.forEach((category, i) => {
      if (i === index) {
        category.isOpen = !category.isOpen;
      } else {
        category.isOpen = false;
      }
    });
  }

  closeAllCategories() {
    this.categories.forEach(category => category.isOpen = false);
  }

  closeSidebar() {
    this.isOpen = false;
    this.closeAllCategories();
  }

  openSidebar() {
    this.isOpen = true;
  }
}
