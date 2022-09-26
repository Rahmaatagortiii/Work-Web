import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SponsordComponent } from './sponsord.component';

describe('SponsordComponent', () => {
  let component: SponsordComponent;
  let fixture: ComponentFixture<SponsordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SponsordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SponsordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
