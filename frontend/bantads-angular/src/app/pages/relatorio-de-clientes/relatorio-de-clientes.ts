import { Component } from '@angular/core';

@Component({
  selector: 'app-relatorio-de-clientes',
  imports: [],
  templateUrl: './relatorio-de-clientes.html',
  styleUrl: './relatorio-de-clientes.css',
})
export class RelatorioDeClientes {
clientes = [
  {
    cpf: '102.345.678-90',
    nome: 'Amanda Costa',
    email: 'amanda.costa@email.com',
    salario: 3200,
    numeroConta: '000123-4',
    saldo: 1500.50,
    limite: 2000,
    gerenteCpf: '123.456.789-01',
    gerenteNome: 'Carlos Silva'
  },
  {
    cpf: '203.456.789-01',
    nome: 'Bruno Almeida',
    email: 'bruno.almeida@email.com',
    salario: 2800,
    numeroConta: '000234-5',
    saldo: -350.75,
    limite: 1500,
    gerenteCpf: '234.567.890-12',
    gerenteNome: 'Ana Pereira'
  },
  {
    cpf: '304.567.890-12',
    nome: 'Camila Ribeiro',
    email: 'camila.ribeiro@email.com',
    salario: 4100,
    numeroConta: '000345-6',
    saldo: 2750.00,
    limite: 3000,
    gerenteCpf: '345.678.901-23',
    gerenteNome: 'Roberto Souza'
  },
  {
    cpf: '405.678.901-23',
    nome: 'Daniel Souza',
    email: 'daniel.souza@email.com',
    salario: 3500,
    numeroConta: '000456-7',
    saldo: 890.40,
    limite: 2500,
    gerenteCpf: '456.789.012-34',
    gerenteNome: 'Juliana Costa'
  },
  {
    cpf: '506.789.012-34',
    nome: 'Eduarda Martins',
    email: 'eduarda.martins@email.com',
    salario: 2900,
    numeroConta: '000567-8',
    saldo: -120.00,
    limite: 1800,
    gerenteCpf: '567.890.123-45',
    gerenteNome: 'Fernando Almeida'
  },
  {
    cpf: '607.890.123-45',
    nome: 'Felipe Gomes',
    email: 'felipe.gomes@email.com',
    salario: 5000,
    numeroConta: '000678-9',
    saldo: 3200.75,
    limite: 4000,
    gerenteCpf: '678.901.234-56',
    gerenteNome: 'Mariana Rocha'
  },
  {
    cpf: '708.901.234-56',
    nome: 'Gabriela Rocha',
    email: 'gabriela.rocha@email.com',
    salario: 3700,
    numeroConta: '000789-0',
    saldo: 1100.00,
    limite: 2200,
    gerenteCpf: '789.012.345-67',
    gerenteNome: 'Ricardo Gomes'
  },
  {
    cpf: '809.012.345-67',
    nome: 'Henrique Carvalho',
    email: 'henrique.carvalho@email.com',
    salario: 2600,
    numeroConta: '000890-1',
    saldo: -500.25,
    limite: 1600,
    gerenteCpf: '890.123.456-78',
    gerenteNome: 'Patricia Martins'
  },
  {
    cpf: '910.123.456-78',
    nome: 'Isabela Pereira',
    email: 'isabela.pereira@email.com',
    salario: 4300,
    numeroConta: '000901-2',
    saldo: 2100.90,
    limite: 3500,
    gerenteCpf: '901.234.567-89',
    gerenteNome: 'Eduardo Carvalho'
  },
  {
    cpf: '011.234.567-89',
    nome: 'João Silva',
    email: 'joao.silva@email.com',
    salario: 3000,
    numeroConta: '001012-3',
    saldo: 750.00,
    limite: 2000,
    gerenteCpf: '012.345.678-90',
    gerenteNome: 'Camila Ribeiro'
  }
];
}
