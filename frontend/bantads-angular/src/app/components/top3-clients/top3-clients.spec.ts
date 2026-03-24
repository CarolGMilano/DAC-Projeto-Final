import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Top3Clients } from './top3-clients';

describe('Top3Clients', () => {
  let component: Top3Clients;
  let fixture: ComponentFixture<Top3Clients>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Top3Clients]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Top3Clients);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
