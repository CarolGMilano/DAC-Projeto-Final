import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatorioDeClientes } from './relatorio-de-clientes';

describe('RelatorioDeClientes', () => {
  let component: RelatorioDeClientes;
  let fixture: ComponentFixture<RelatorioDeClientes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RelatorioDeClientes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelatorioDeClientes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
