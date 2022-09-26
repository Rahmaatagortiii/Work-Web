import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowCollaborationComponent } from './show-collaboration.component';

describe('ShowCollaborationComponent', () => {
  let component: ShowCollaborationComponent;
  let fixture: ComponentFixture<ShowCollaborationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowCollaborationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowCollaborationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
