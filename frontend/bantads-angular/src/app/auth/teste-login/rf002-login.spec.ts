import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Rf002Login } from './rf002-login';

describe('Rf002Login', () => {
  let component: Rf002Login;
  let fixture: ComponentFixture<Rf002Login>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Rf002Login]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Rf002Login);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
