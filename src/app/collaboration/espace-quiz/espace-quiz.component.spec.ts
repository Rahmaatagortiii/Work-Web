import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EspaceQuizComponent } from './espace-quiz.component';

describe('EspaceQuizComponent', () => {
  let component: EspaceQuizComponent;
  let fixture: ComponentFixture<EspaceQuizComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EspaceQuizComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EspaceQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
