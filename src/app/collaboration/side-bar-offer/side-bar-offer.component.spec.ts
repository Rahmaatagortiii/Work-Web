import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideBarOfferComponent } from './side-bar-offer.component';

describe('SideBarOfferComponent', () => {
  let component: SideBarOfferComponent;
  let fixture: ComponentFixture<SideBarOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SideBarOfferComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SideBarOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
