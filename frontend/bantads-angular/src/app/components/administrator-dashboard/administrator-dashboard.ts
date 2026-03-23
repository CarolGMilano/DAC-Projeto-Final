import { Component } from '@angular/core';

@Component({
  selector: 'app-administrator-dashboard',
  imports: [],
  templateUrl: './administrator-dashboard.html',
  styleUrl: './administrator-dashboard.css',
})
export class AdministratorDashboard {
exemplos = [
  { name: 'Carlos Silva', clientes: 42, saldoPositivo: 185000.50, saldoNegativo: -12000.75 },
  { name: 'Ana Pereira', clientes: 38, saldoPositivo: 172340.20, saldoNegativo: -9800.00 },
  { name: 'Roberto Souza', clientes: 31, saldoPositivo: 160890.00, saldoNegativo: -15000.10 },
  { name: 'Juliana Costa', clientes: 29, saldoPositivo: 149500.75, saldoNegativo: -7200.50 },
  { name: 'Fernando Almeida', clientes: 34, saldoPositivo: 141230.90, saldoNegativo: -5400.00 },
  { name: 'Mariana Rocha', clientes: 27, saldoPositivo: 132800.40, saldoNegativo: -8600.20 },
  { name: 'Ricardo Gomes', clientes: 25, saldoPositivo: 120450.10, saldoNegativo: -4300.00 },
  { name: 'Patricia Martins', clientes: 23, saldoPositivo: 110320.00, saldoNegativo: -9100.60 },
  { name: 'Eduardo Carvalho', clientes: 21, saldoPositivo: 98750.30, saldoNegativo: -2500.00 },
  { name: 'Camila Ribeiro', clientes: 19, saldoPositivo: 86540.80, saldoNegativo: -3100.40 }
];
}
