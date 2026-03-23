import { Component, inject, ViewChild } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, NgForm, NgModel } from '@angular/forms';

import { CepStatus, ICliente, IEndereco, TipoUsuario, SharedModule, EtapaCadastro } from '../../../shared';
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

  private router = inject(Router);

  readonly _jsonEnderecoService = inject(EnderecoService);
  readonly clienteService = inject(ClienteService);

  etapa: EtapaCadastro = 1;
  EtapaCadastro = EtapaCadastro;

  endereco: IEndereco = {
    cep: '',
    logradouro: '',
    numero: undefined,
    complemento: '',
    cidade: '',
    estado: '',
  };

  cliente: ICliente = {
    nome: '',
    cpf: '',
    telefone: '',
    email: '',
    estadoCivil: '',
    salario: null,
    endereco: this.endereco
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
               this.formCadastro.controls['telefone']?.valid &&
               this.formCadastro.controls['estadoCivil']?.valid;

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
    this.endereco.logradouro = '';
    this.endereco.numero = undefined;
    this.endereco.complemento = '';
    this.endereco.cidade = '';
    this.endereco.estado = '';
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
            this.endereco.logradouro = response.logradouro;
            this.endereco.cidade = response.localidade;
            this.endereco.estado = response.estado;
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

    const cliente: ICliente = {
      id: -1,
      tipo: TipoUsuario.CLIENTE,
      nome: this.cliente.nome,
      cpf: this.cliente.cpf,
      telefone: this.cliente.telefone,
      email: this.cliente.email,
      estadoCivil: this.cliente.estadoCivil,
      salario: this.salarioParaNumero(this.salarioFormatado),
      endereco: {
        cep: this.endereco.cep,
        logradouro: this.cliente.endereco.logradouro,
        numero: this.cliente.endereco.numero,
        complemento: this.cliente.endereco.complemento,
        cidade: this.cliente.endereco.cidade,
        estado: this.cliente.endereco.estado,
      }
    }

    this.clienteService.salvar(cliente);

    this.cadastroConcluido = true;
  }
}