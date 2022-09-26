import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfOffersComponent } from './pdf-offers.component';

describe('PdfOffersComponent', () => {
  let component: PdfOffersComponent;
  let fixture: ComponentFixture<PdfOffersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PdfOffersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
