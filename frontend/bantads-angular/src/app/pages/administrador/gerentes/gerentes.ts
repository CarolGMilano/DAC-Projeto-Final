import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm, NgModel } from '@angular/forms';

import { SharedModule, IGerente, TipoUsuario } from '../../../shared';
import { GerenteService } from '../../../services';
import { Observable } from 'rxjs';
import { Loading } from '../../../components';

@Component({
  selector: 'app-gerentes',
  imports: [CommonModule, FormsModule, SharedModule, Loading],
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
    nome: '',
    cpf: '',
    email: '',
    senha: '',
    tipo: TipoUsuario.GERENTE
  }

  pesquisa: string = '';

  mostrarFormulario: boolean = false;
  mostrarPopupExclusao: boolean = false;
  mostrarSenha: boolean = true;
  mostrarNovaSenha: boolean = true;

  senhaIncorreta: boolean = false;

  loading: boolean = false;

  modoFormulario: 'nenhum' | 'adicionar' | 'editar' = 'nenhum';

  ngOnInit(){
    this.listarTodos();
  }

  abrirAdicionar() {
    this.gerente = {
      nome: '',
      cpf: '',
      email: '',
      senha: '',
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
    this.loading = true;

    this.gerenteService.listarTodos().subscribe({
      next: (gerentes) => {
        this.gerentes = (gerentes ?? []).sort((gerente1, gerente2) =>
          (gerente1.nome ?? '').localeCompare(gerente2.nome ?? '')
        );
        this.loading = false;
      },
      error: (erro) => {
        this.loading = false;
        if (erro.status === 500) {
          alert(`Erro interno: ${erro.error}`);
        } else {
          alert('Erro inesperado ao listar funcionários.');
          console.log(erro);
        }
      }
    });
  }

  salvar(): void {
    this.senhaIncorreta = false;
    this.loading = true;

    if (!this.formGerentes.form.valid) return;

    if (this.modoFormulario === 'adicionar') {
      this.gerenteService.inserir(this.gerente).subscribe({
        next: () => {
          this.listarTodos();
          this.mostrarFormulario = false;
          this.formGerentes.reset();
          this.cancelar();
          this.loading = false;
        },
        error: (erro) => {
          this.loading = false;
          if (erro.status === 409) {
            if (erro.error.tipo === 'cpf') {
              this.cpfModel.control.setErrors({ cpfConflito: true });
              return;
            }

            if (erro.error.tipo === 'email') {
              this.emailModel.control.setErrors({ emailConflito: true });
              return;
            }
          }

          if (erro.status === 404) {
            alert(`Não encontrado: ${erro.error}`);
            return;
          }

          if (erro.status === 500) {
            alert(`Erro interno: ${erro.error}`);
            return;
          }

          alert('Erro inesperado ao criar gerente.');
        }
      });
    } else {
      this.gerenteService.atualizar(this.gerente).subscribe({
        next: () => {
          this.listarTodos();
          this.mostrarFormulario = false;
          this.formGerentes.reset();
          this.cancelar();
          this.loading = false;
        },
        error: (erro) => {
          this.loading = false;
          if (erro.status === 409 && erro.error.tipo === 'email') {
            this.emailModel.control.setErrors({ emailConflito: true });
            return;
          }

          if (erro.status === 404) {
            alert(`Não encontrado: ${erro.error}`);
            return;
          }

          alert('Erro inesperado ao atualizar gerente.');
        }
      });
    }
  }

  excluir() {
    if (!this.gerente.cpf) return;
    this.loading = true;

    this.gerenteService.remover(this.gerente.cpf).subscribe({
      next: () => {
        this.listarTodos();
        this.mostrarPopupExclusao = false;
        this.loading = false;
      },

      error: (erro) => {
        this.loading = false;
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