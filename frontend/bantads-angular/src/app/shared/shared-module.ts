import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Numericos, CpfValidator, TelefoneValidator } from './directives';
import { CpfFormatadorPipe, TelefoneFormatadorPipe } from './pipes';

@NgModule({
  declarations: [
    Numericos,
    CpfValidator,
    TelefoneValidator,

    CpfFormatadorPipe,
    TelefoneFormatadorPipe
  ],
  exports: [
    Numericos,
    CpfValidator,
    TelefoneValidator,

    CpfFormatadorPipe,
    TelefoneFormatadorPipe
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
