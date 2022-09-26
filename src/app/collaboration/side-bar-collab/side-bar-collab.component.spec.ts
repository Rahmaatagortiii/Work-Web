import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideBarCollabComponent } from './side-bar-collab.component';

describe('SideBarCollabComponent', () => {
  let component: SideBarCollabComponent;
  let fixture: ComponentFixture<SideBarCollabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SideBarCollabComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SideBarCollabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
