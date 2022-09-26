import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QaWidgetComponent } from './qa-widget.component';

describe('QaWidgetComponent', () => {
  let component: QaWidgetComponent;
  let fixture: ComponentFixture<QaWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QaWidgetComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QaWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
