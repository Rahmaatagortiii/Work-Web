import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertismentCarouselComponent } from './advertisment-carousel.component';

describe('AdvertismentCarouselComponent', () => {
  let component: AdvertismentCarouselComponent;
  let fixture: ComponentFixture<AdvertismentCarouselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdvertismentCarouselComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdvertismentCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
