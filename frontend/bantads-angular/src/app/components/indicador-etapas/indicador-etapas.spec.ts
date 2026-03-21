import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndicadorEtapas } from './indicador-etapas';

describe('IndicadorEtapas', () => {
  let component: IndicadorEtapas;
  let fixture: ComponentFixture<IndicadorEtapas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndicadorEtapas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndicadorEtapas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
