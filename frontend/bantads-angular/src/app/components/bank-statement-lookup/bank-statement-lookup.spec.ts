import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankStatementLookup } from './bank-statement-lookup';

describe('BankStatementLookup', () => {
  let component: BankStatementLookup;
  let fixture: ComponentFixture<BankStatementLookup>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BankStatementLookup]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankStatementLookup);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
