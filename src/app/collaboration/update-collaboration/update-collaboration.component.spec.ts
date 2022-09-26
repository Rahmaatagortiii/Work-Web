import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCollaborationComponent } from './update-collaboration.component';

describe('UpdateCollaborationComponent', () => {
  let component: UpdateCollaborationComponent;
  let fixture: ComponentFixture<UpdateCollaborationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCollaborationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCollaborationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
