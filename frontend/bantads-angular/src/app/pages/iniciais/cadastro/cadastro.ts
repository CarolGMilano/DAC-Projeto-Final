import { Component, inject, ViewChild } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, NgForm, NgModel } from '@angular/forms';

import { CepStatus, SharedModule, EtapaCadastro, IAutocadastro } from '../../../shared';

import { ClienteService, EnderecoService } from '../../../services';
import { CommonModule } from '@angular/common';
import { IndicadorEtapas, Loading } from "../../../components";

@Component({
  selector: 'app-cadastro',
  imports: [CommonModule, FormsModule, RouterLink, SharedModule, IndicadorEtapas, Loading],
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

  cliente: IAutocadastro = {
    cpf: '',
    email: '',
    nome: '',
    telefone: '',
    salario: null,
    endereco: '',
    CEP: '',
    cidade: '',
    estado: '',
  };

  salarioFormatado: string = '';

  mensagemErro: string | null = null;

  cepStatus: CepStatus = CepStatus.Vazio;
  status = CepStatus;
  cadastroConcluido: boolean = false;

  loading: boolean = false;

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

    this.loading = true;

    const novoCliente: IAutocadastro = {
      cpf: this.cliente.cpf,
      email: this.cliente.email,
      nome: this.cliente.nome,
      telefone: this.cliente.telefone,
      salario: this.salarioParaNumero(this.salarioFormatado),
      endereco: `${this.cliente.endereco}${this.numero !== undefined ? ', ' + this.numero : ''}`,
      CEP: this.cliente.CEP,
      cidade: this.cliente.cidade,
      estado: this.cliente.estado,
    }

    this.clienteService.inserir(novoCliente).subscribe({
      next: () => {
        this.cadastroConcluido = true;
        this.loading = false;
        this.mensagemErro = null;
      },
      error: (erro) => {
        this.cadastroConcluido = true;
        this.loading = false;

        if (erro.status === 409) {
          if (erro.error.tipo === 'cpf') {
            this.mensagemErro = 'cpf';
          } else if (erro.error.tipo === 'email') {
            this.mensagemErro = 'email';
          } else {
            this.mensagemErro = 'Conflito de dados.';
          }

        } else if (erro.status === 500) {
          this.mensagemErro = 'Erro interno no servidor. Tente novamente mais tarde.';
          console.log(erro)

        } else {
          this.mensagemErro = 'Erro inesperado ao cadastrar cliente.';
        }
      }
    });
  }
}