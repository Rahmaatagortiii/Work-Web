import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlideBarQuizComponent } from './slide-bar-quiz.component';

describe('SlideBarQuizComponent', () => {
  let component: SlideBarQuizComponent;
  let fixture: ComponentFixture<SlideBarQuizComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SlideBarQuizComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SlideBarQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
