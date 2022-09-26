import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCollaborationComponent } from './add-collaboration.component';

describe('AddCollaborationComponent', () => {
  let component: AddCollaborationComponent;
  let fixture: ComponentFixture<AddCollaborationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCollaborationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCollaborationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
