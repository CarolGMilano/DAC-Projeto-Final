import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileChange } from './profile-change';

describe('ProfileChange', () => {
  let component: ProfileChange;
  let fixture: ComponentFixture<ProfileChange>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfileChange]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileChange);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
