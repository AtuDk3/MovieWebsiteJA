import { Component } from '@angular/core';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.scss'
})
export class RatingComponent {

  rating: number = 3; // Giá trị mặc định

  // Các mô tả cho từng mức đánh giá
  ratingDescriptions: { [key: number]: string } = {
    1: 'Rất tệ',
    2: 'Tệ',
    3: 'Bình thường',
    4: 'Tốt',
    5: 'Xuất sắc'
  };

  get ratingDescription(): string {
    return this.ratingDescriptions[this.rating];
  }

  constructor(public dialogRef: MatDialogRef<RatingDialogComponent>) {}

  onRatingChange(newRating: number): void {
    this.rating = newRating;
  }

  submitRating(): void {
    console.log('User rated:', this.rating);
    this.dialogRef.close(this.rating);
    // Thực hiện hành động khác, ví dụ: gửi giá trị này đến server
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
