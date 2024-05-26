import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieRelatedComponent } from './movie-related.component';

describe('MovieRelatedComponent', () => {
  let component: MovieRelatedComponent;
  let fixture: ComponentFixture<MovieRelatedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MovieRelatedComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MovieRelatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
