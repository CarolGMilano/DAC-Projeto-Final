import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm, NgModel } from '@angular/forms';

import { SharedModule, IGerente, TipoUsuario } from '../../../shared';
import { GerenteService } from '../../../services';

@Component({
  selector: 'app-gerentes',
  imports: [CommonModule, FormsModule, SharedModule],
  templateUrl: './gerentes.html',
  styleUrl: './gerentes.css',
})
export class Gerentes implements OnInit{
  @ViewChild('formGerentes') formGerentes! : NgForm;
  @ViewChild('cpf') cpfModel!: NgModel;
  @ViewChild('email') emailModel!: NgModel;

  private gerenteService = inject(GerenteService);

  gerentes: IGerente[] = [];

  gerente: IGerente = {
    id: -1, 
    //Só para testes, pois no fluxo da SAGA, este id virá do MSAuth.
    idUsuario: Math.floor(Math.random() * 1000000000),
    nome: '',
    cpf: '',
    email: '',
    telefone: '',
    senhaAtual: '',
    novaSenha: '',
    ativo: true,
    tipo: TipoUsuario.GERENTE
  }

  pesquisa: string = '';

  mostrarFormulario: boolean = false;
  mostrarPopupExclusao: boolean = false;
  mostrarSenha: boolean = true;
  mostrarNovaSenha: boolean = true;

  senhaIncorreta: boolean = false;

  modoFormulario: 'nenhum' | 'adicionar' | 'editar' = 'nenhum';

  ngOnInit(){
    this.listarTodos();
  }

  abrirAdicionar() {
    this.gerente = {
      id: -1, 
      idUsuario: Math.floor(Math.random() * 1000000000),
      nome: '',
      cpf: '',
      email: '',
      telefone: '',
      senhaAtual: '',
      novaSenha: '',
      ativo: true,
      tipo: TipoUsuario.GERENTE
    }

    this.modoFormulario = 'adicionar';
    this.mostrarFormulario = true;
  }

  abrirEditar(gerenteSelecionado: IGerente) {
    this.gerente = { ... gerenteSelecionado };

    this.modoFormulario = 'editar';
    this.mostrarFormulario = true;
  }
  
  abrirExcluir(gerenteSelecionado: IGerente) {
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

  listarTodos() {
    this.gerenteService.listarTodos().subscribe({
      next: (gerentes) => {
        this.gerentes = (gerentes ?? []).sort((gerente1, gerente2) =>
          (gerente1.nome ?? '').localeCompare(gerente2.nome ?? '')
        );
      },
      error: (erro) => {
        if (erro.status === 500) {
          alert(`Erro interno: ${erro.error}`);
        } else {
          alert('Erro inesperado ao listar funcionários.');
        }
      }
    });
  }

  salvar(): void {
    this.senhaIncorreta = false;

    if (!this.formGerentes.form.valid) return;

    if (this.modoFormulario === 'adicionar') {

      this.gerenteService.inserir(this.gerente).subscribe({
        next: () => {
          this.listarTodos();
          this.mostrarFormulario = false;
          this.formGerentes.reset();
          this.cancelar();
        },
        error: (erro) => {
          if (erro.status === 409) {
            if (erro.error.tipo === 'cpf') {
              this.cpfModel.control.setErrors({ cpfConflito: true });
            } else if (erro.error.tipo === 'email') {
              this.emailModel.control.setErrors({ emailConflito: true });
            }
          } else if (erro.status === 500) {
            alert(`Erro interno: ${erro.error}`);
          } else {
            alert('Erro inesperado ao cadastrar gerente.');
          }
        }
      });

    } else {

      this.gerenteService.atualizar(this.gerente).subscribe({
        next: () => {
          this.listarTodos();
          this.mostrarFormulario = false;
          this.formGerentes.reset();
          this.cancelar();
        },
        error: (erro) => {
          if (erro.status === 404) {
            alert(`Não encontrado: ${erro.error}`);
          } else if (erro.status === 500) {
            alert(`Erro interno: ${erro.error}`);
          } else {
            alert('Erro inesperado ao atualizar gerente.');
          }
        }
      });
    }
  }

  excluir() {
    if (!this.gerente.cpf) return;

    this.gerenteService.remover(this.gerente.cpf).subscribe({
      next: () => {
        this.listarTodos();
        this.mostrarPopupExclusao = false;
      },

      error: (erro) => {
        if (erro.status === 404) {
          alert(`Não encontrado: ${erro.error}`);
        } else if (erro.status === 500) {
          alert(`Erro interno: ${erro.error}`);
        } else {
          alert('Erro inesperado ao excluir o gerente.');
        }
      }
    });
  }
}