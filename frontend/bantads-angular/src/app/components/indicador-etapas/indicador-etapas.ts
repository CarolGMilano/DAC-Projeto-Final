import { Component, Input } from '@angular/core';

import { EtapaCadastro } from '../../shared';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-indicador-etapas',
  imports: [CommonModule, FormsModule],
  templateUrl: './indicador-etapas.html',
  styleUrl: './indicador-etapas.css',
})
export class IndicadorEtapas {
  @Input() etapaAtual!: EtapaCadastro;

  EtapaCadastro = EtapaCadastro;

  estaAtiva(etapa: EtapaCadastro): boolean {
    return this.etapaAtual === etapa;
  }

  estaConcluida(etapa: EtapaCadastro): boolean {
    return this.etapaAtual > etapa;
  }
}
