import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

import { SharedModule, Gerente } from '../../../shared';

@Component({
  selector: 'app-gerentes',
  imports: [CommonModule, FormsModule, SharedModule],
  templateUrl: './gerentes.html',
  styleUrl: './gerentes.css',
})
export class Gerentes implements OnInit{
  @ViewChild('formGerentes') formGerentes! : NgForm;

  gerentes: any[] = [
    {
      id: 1,
      nome: 'João Pedro Almeida',
      cpf: '12345678901',
      email: 'joao.almeida@empresa.com',
      telefone: '41988123456',
      saldoPositivo: 12500.50,
      saldoNegativo: -3200.75
    },
    {
      id: 2,
      nome: 'Mariana Costa Ribeiro',
      cpf: '23456789012',
      email: 'mariana.ribeiro@empresa.com',
      telefone: '41987452198',
      saldoPositivo: 9800.00,
      saldoNegativo: -1500.20
    },
    {
      id: 3,
      nome: 'Lucas Fernandes Rocha',
      cpf: '34567890123',
      email: 'lucas.rocha@empresa.com',
      telefone: '41991023344',
      saldoPositivo: 15700.30,
      saldoNegativo: -4200.00
    },
    {
      id: 4,
      nome: 'Beatriz Martins Lopes',
      cpf: '45678901234',
      email: 'beatriz.lopes@empresa.com',
      telefone: '41996547781',
      saldoPositivo: 8700.90,
      saldoNegativo: -980.40
    },
    {
      id: 5,
      nome: 'Rafael Gomes Duarte',
      cpf: '56789012345',
      email: 'rafael.duarte@empresa.com',
      telefone: '41984219902',
      saldoPositivo: 13200.00,
      saldoNegativo: -2750.10
    },
    {
      id: 6,
      nome: 'Camila Nogueira Pinto',
      cpf: '67890123456',
      email: 'camila.pinto@empresa.com',
      telefone: '41992331147',
      saldoPositivo: 11050.75,
      saldoNegativo: -3100.00
    },
    {
      id: 7,
      nome: 'Felipe Andrade Batista',
      cpf: '78901234567',
      email: 'felipe.batista@empresa.com',
      telefone: '41985674432',
      saldoPositivo: 9400.60,
      saldoNegativo: -1200.50
    },
    {
      id: 8,
      nome: 'Juliana Teixeira Moraes',
      cpf: '89012345678',
      email: 'juliana.moraes@empresa.com',
      telefone: '41998017765',
      saldoPositivo: 17600.00,
      saldoNegativo: -5300.25
    },
    {
      id: 9,
      nome: 'Gustavo Carvalho Freitas',
      cpf: '90123456789',
      email: 'gustavo.freitas@empresa.com',
      telefone: '41987902214',
      saldoPositivo: 10200.10,
      saldoNegativo: -2100.00
    },
    {
      id: 10,
      nome: 'Patrícia Oliveira Barros',
      cpf: '01234567890',
      email: 'patricia.barros@empresa.com',
      telefone: '41991776033',
      saldoPositivo: 8900.00,
      saldoNegativo: -950.80
    }
  ];

  gerente: any = {
    id: -1,
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
    saldoPositivo: 0,
    saldoNegativo: 0
  }

  pesquisa: string = '';

  mostrarFormulario: boolean = false;
  mostrarPopupExclusao: boolean = false;
  mostrarSenha: boolean = true;
  mostrarNovaSenha: boolean = true;

  senhaIncorreta: boolean = false;

  modoFormulario: 'nenhum' | 'adicionar' | 'editar' = 'nenhum';

  ngOnInit(){
    this.ordenarGerentes();
  }

  ordenarGerentes() {
    this.gerentes.sort((gerente1, gerente2) => gerente1.nome.localeCompare(gerente2.nome));
  }

  get gerentesFiltrados() {
    return this.gerentes.filter(gerente =>
      gerente.nome.toLowerCase().includes(this.pesquisa.toLowerCase())
    );
  }

  abrirAdicionar() {
    this.gerente = {
      id: -1,
      nome: '',
      cpf: '',
      email: '',
      telefone: '',
      senhaAtual: '',
      saldoPositivo: 0,
      saldoNegativo: 0
    }

    this.modoFormulario = 'adicionar';
    this.mostrarFormulario = true;
  }

  abrirEditar(gerenteSelecionado: Gerente) {
    this.gerente = { ... gerenteSelecionado };

    this.modoFormulario = 'editar';
    this.mostrarFormulario = true;
  }
  
  abrirExcluir(gerenteSelecionado: Gerente) {
    this.gerente = { ... gerenteSelecionado };
    this.mostrarPopupExclusao = true;
  }

  cancelar() {
    if(this.modoFormulario == 'adicionar' || this.modoFormulario == 'editar'){
      this.mostrarFormulario = false;
      this.mostrarNovaSenha = true;
      this.mostrarSenha = true;
    } else {
      this.mostrarPopupExclusao = false;
      this.mostrarNovaSenha = true;
      this.mostrarSenha = true;
    }

    this.modoFormulario = 'nenhum';
  }

  salvar(): void {
    if (this.modoFormulario === 'adicionar') {
      const novoGerente: any = {
        id: this.gerentes.length + 1,
        nome: this.gerente.nome,
        cpf: this.gerente.cpf,
        email: this.gerente.email,
        telefone: this.gerente.telefone,
        senhaAtual: this.gerente.senhaAtual,
        saldoNegativo: this.gerente.saldoNegativo,
        saldoPositivo: this.gerente.saldoPositivo
      
      };

      this.gerentes.push(novoGerente);
      this.ordenarGerentes();
    } else if (this.modoFormulario === 'editar') {
      const index = this.gerentes.findIndex(
        gerente => gerente.cpf === this.gerente.cpf
      );

      if (index !== -1) {
        this.gerentes[index] = {
          ...this.gerentes[index],
          nome: this.gerente.nome,
          email: this.gerente.email,
          telefone: this.gerente.telefone
        };
      }
    }

    this.cancelar();
    this.ordenarGerentes();
  }

  excluir() {
    this.gerentes = this.gerentes.filter(gerente => gerente.cpf !== this.gerente?.cpf);
    this.mostrarPopupExclusao = false;
  }
}
