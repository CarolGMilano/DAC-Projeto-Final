import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Rf001Autocadastro } from './rf001-autocadastro';

describe('Rf001Autocadastro', () => {
  let component: Rf001Autocadastro;
  let fixture: ComponentFixture<Rf001Autocadastro>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Rf001Autocadastro]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Rf001Autocadastro);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
