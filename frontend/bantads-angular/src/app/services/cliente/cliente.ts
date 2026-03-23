import { Injectable } from '@angular/core';
import { ICliente } from '../../shared';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  private storageKey = 'clientes';

  constructor() {
      this.initSeed();
    }

  private initSeed(): void {
  if (!localStorage.getItem(this.storageKey)) {
    const clientes = [
  {
    id: 1,
    tipo: 1,
    numeroConta: '1001-5',
    nome: 'João Silva',
    cpf: '52998224725',
    telefone: '41999990001',
    email: 'joao.silva@email.com',
    estadoCivil: 'Solteiro',
    salario: 3000,
    saldo: 10000,
    limite: 8000,
    gerenteNome: 'Lucas Oliveira',
    gerenteCpf: '39053344705',
    endereco: {
      id: 1,
      cep: '80010-000',
      logradouro: 'Rua das Flores',
      numero: 123,
      complemento: 'Apto 101',
      cidade: 'Curitiba',
      estado: 'PR'
    }
  },
  {
    id: 2,
    tipo: 1,
    numeroConta: '1002-3',
    nome: 'Maria Souza',
    cpf: '16899535009',
    telefone: '41999990002',
    email: 'maria.souza@email.com',
    estadoCivil: 'Casada',
    salario: 4500,
    saldo: 9000,
    limite: 12000,
    gerenteNome: 'Lucas Oliveira',
    gerenteCpf: '39053344705',
    endereco: {
      id: 2,
      cep: '80020-000',
      logradouro: 'Av. Paraná',
      numero: 456,
      cidade: 'Curitiba',
      estado: 'PR'
    }
  },
  {
    id: 3,
    tipo: 1,
    numeroConta: '1003-8',
    nome: 'Pedro Santos',
    cpf: '45317828791',
    telefone: '41999990003',
    email: 'pedro.santos@email.com',
    estadoCivil: 'Solteiro',
    salario: 2500,
    saldo: 8000,
    limite: 5000,
    gerenteNome: 'Lucas Oliveira',
    gerenteCpf: '39053344705',
    endereco: {
      id: 3,
      cep: '80030-000',
      logradouro: 'Rua XV de Novembro',
      numero: 789,
      cidade: 'Curitiba',
      estado: 'PR'
    }
  },
  {
    id: 4,
    tipo: 1,
    numeroConta: '1004-1',
    nome: 'Ana Oliveira',
    cpf: '29537914806',
    telefone: '41999990004',
    email: 'ana.oliveira@email.com',
    estadoCivil: 'Divorciada',
    salario: 5200,
    saldo: 7000,
    limite: 15000,
    gerenteNome: 'Lucas Oliveira',
    gerenteCpf: '39053344705',
    endereco: {
      id: 4,
      cep: '80040-000',
      logradouro: 'Rua Marechal Deodoro',
      numero: 321,
      complemento: 'Casa',
      cidade: 'Curitiba',
      estado: 'PR'
    }
  },
  {
    id: 5,
    tipo: 1,
    numeroConta: '1005-9',
    nome: 'Lucas Pereira',
    cpf: '86288366757',
    telefone: '41999990005',
    email: 'lucas.pereira@email.com',
    estadoCivil: 'Casado',
    salario: 3800,
    saldo: 6000,
    limite: 7000,
    gerenteNome: 'Lucas Oliveira',
    gerenteCpf: '39053344705',
    endereco: {
      id: 5,
      cep: '80050-000',
      logradouro: 'Rua Chile',
      numero: 654,
      cidade: 'Curitiba',
      estado: 'PR'
    }
  }
];

    localStorage.setItem(this.storageKey, JSON.stringify(clientes));
  }
}


  salvar(cliente: ICliente) {
    console.log(cliente);
  }

  private getData(): ICliente[] {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data) : [];
  }

  private setData(clientes: ICliente[]): void {
    localStorage.setItem(this.storageKey, JSON.stringify(clientes));
  }

  // GET
  get(): ICliente[] {
    return this.getData();
  }

  getById(id: number): any {
    const clientes = this.getData();
    return clientes.find(c => c.id === id);
  }

  getByCPF(cpf: string): any {
    const clientes = this.getData();
    return clientes.find(c => c.cpf === cpf);
  }

  // POST
  post(cliente: ICliente): void {
    const clientes = this.getData();
    clientes.push(cliente);
    this.setData(clientes);
  }

  // PUT (usa cpf como identificador)
  put(cpf: string, clienteAtualizado: ICliente): void {
    const clientes = this.getData();
    const index = clientes.findIndex(c => c.cpf === cpf);

    if (index !== -1) {
      clientes[index] = clienteAtualizado;
      this.setData(clientes);
    }
  }

  // DELETE
  delete(cpf: string): void {
    const clientes = this.getData();
    const filtrados = clientes.filter(c => c.cpf !== cpf);
    this.setData(filtrados);
  }


}
