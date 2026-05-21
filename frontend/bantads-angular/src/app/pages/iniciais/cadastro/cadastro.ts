import { Component, inject, ViewChild } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, NgForm, NgModel } from '@angular/forms';

import { CepStatus, SharedModule, EtapaCadastro } from '../../../shared';

import { ICliente2 } from '../../../shared/models/ICliente_V2';

import { ClienteService, EnderecoService } from '../../../services';
import { CommonModule } from '@angular/common';
import { IndicadorEtapas } from "../../../components";

@Component({
  selector: 'app-cadastro',
  imports: [CommonModule, FormsModule, RouterLink, SharedModule, IndicadorEtapas],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css',
})
export class Cadastro {
  @ViewChild('formCadastro') formCadastro! : NgForm;
  @ViewChild('cpf') cpfModel!: NgModel;
  @ViewChild('email') emailModel!: NgModel;

  private router = inject(Router);

  readonly _jsonEnderecoService = inject(EnderecoService);
  readonly clienteService = inject(ClienteService);

  etapa: EtapaCadastro = 1;
  EtapaCadastro = EtapaCadastro;

  numero: number | undefined;

  cliente: ICliente2 = {
    id: -1,
    cpf: '',
    email: '',
    nome: '',
    telefone: '',
    salario: null,
    endereco: '',
    cep: '',
    cidade: '',
    estado: '',
  };

  salarioFormatado: string = '';

  cepStatus: CepStatus = CepStatus.Vazio;
  status = CepStatus;
  cadastroConcluido: boolean = false;

  validarEtapaAtual(): boolean {
    if (!this.formCadastro) return false;

    switch (this.etapa) {
      case EtapaCadastro.DADOS_PESSOAIS:
        return this.formCadastro.controls['nome']?.valid &&
               this.formCadastro.controls['cpf']?.valid &&
               this.formCadastro.controls['email']?.valid &&
               this.formCadastro.controls['telefone']?.valid;

      case EtapaCadastro.ENDERECO:
        return this.formCadastro.controls['cep']?.valid;

      case EtapaCadastro.DADOS_FINANCEIROS:
        return this.formCadastro.controls['salario']?.valid;

      default:
        return true;
    }
  }

  proximaEtapa() {
    if (this.etapa < EtapaCadastro.RESUMO) {
      this.etapa++;
    }
  }

  voltarEtapa() {
    if (this.etapa > EtapaCadastro.DADOS_PESSOAIS) {
      this.etapa--;
    }
  }

  limpaEndereco() {
    this.cliente.endereco = '';
    this.numero = undefined;
    this.cliente.cidade = '';
    this.cliente.estado = '';
  }

  salarioParaNumero(valor: string): number {
    if (!valor) return 0;

    return Number(valor.replace(/\D/g, '')) / 100;
  }

  validaCEP(cep: string) {
    //Se o CEP for nulo ou indefinido, limpa os campos e retorna.
    if(!cep) {
      this.limpaEndereco();
      this.cepStatus = CepStatus.Vazio;

      return;
    }

    //Se esse CEP tiver 8 digitos
    if(cep.length === 8) {
      //Faz a requisição para a API ViaCEP e retorna o resultado
      this._jsonEnderecoService.getEndereco(cep).subscribe({
        //Sucesso
        next: (response) => {
          if (response && response.logradouro) {
            this.cliente.endereco = response.logradouro;
            this.cliente.cidade = response.localidade;
            this.cliente.estado = response.estado;
            this.cepStatus = CepStatus.Valido;
          } else {
            this.limpaEndereco();
            this.cepStatus = CepStatus.Invalido;
          }
        },
        //Erro
        error: () => {
          this.limpaEndereco();
          this.cepStatus = CepStatus.ProblemaAPI;
        }
      });
    } else {
      this.limpaEndereco();
      this.cepStatus = CepStatus.Incompleto;
    }
  }

  salvar(){
    if (!this.formCadastro.form.valid) return;

    const novoCliente: ICliente2 = {
      cpf: this.cliente.cpf,
      email: this.cliente.email,
      nome: this.cliente.nome,
      telefone: this.cliente.telefone,
      salario: this.salarioParaNumero(this.salarioFormatado),
      endereco: `${this.cliente.endereco}${this.numero !== undefined ? ', ' + this.numero : ''}`,
      cep: this.cliente.cep,
      cidade: this.cliente.cidade,
      estado: this.cliente.estado,
    }

    this.clienteService.inserir(novoCliente).subscribe({
      next: () => {
        this.cadastroConcluido = true;
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
  }
}