import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Numericos, CpfValidator, TelefoneValidator } from './directives';
import { CepFormatadorPipe, CpfFormatadorPipe, EnderecoFormatadorPipe, SalarioFormatadorPipe, TelefoneFormatadorPipe } from './pipes';

@NgModule({
  declarations: [
    Numericos,
    CpfValidator,
    TelefoneValidator,

    CpfFormatadorPipe,
    TelefoneFormatadorPipe,
    SalarioFormatadorPipe,
    EnderecoFormatadorPipe,
    CepFormatadorPipe
  ],
  exports: [
    Numericos,
    CpfValidator,
    TelefoneValidator,

    CpfFormatadorPipe,
    TelefoneFormatadorPipe,
    SalarioFormatadorPipe,
    EnderecoFormatadorPipe,
    CepFormatadorPipe
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
