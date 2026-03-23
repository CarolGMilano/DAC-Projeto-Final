import { Injectable } from '@angular/core';
import { Gerente } from '../../shared';

@Injectable({
  providedIn: 'root',
})
export class GerenteService {
  
  constructor() {
    this.initSeed();
  }

  private storageKey = 'gerentes';

  private initSeed(): void {
    if (!localStorage.getItem(this.storageKey)) {
      const gerentes: Gerente[] = [
        {
          id: 5,
          nome: 'Lucas Oliveira',
          cpf: '56789012345',
          email: 'lucas.oliveira@empresa.com',
          telefone: '41990000005',
          saldoPositivo: 300000.00,
          saldoNegativo: -20000.00,
          numClientes: 74,
        },
        {
          id: 9,
          nome: 'Bruno Rocha',
          cpf: '90123456789',
          email: 'bruno.rocha@empresa.com',
          telefone: '41990000009',
          saldoPositivo: 220000.00,
          saldoNegativo: -17000.00,
          numClientes: 111,
        },
        {
          id: 3,
          nome: 'João Pereira',
          cpf: '34567890123',
          email: 'joao.pereira@empresa.com',
          telefone: '41990000003',
          saldoPositivo: 210000.75,
          saldoNegativo: -15000.25,
          numClientes: 25,
        },
        {
          id: 1,
          nome: 'Carlos Silva',
          cpf: '12345678901',
          email: 'carlos.silva@empresa.com',
          telefone: '41990000001',
          saldoPositivo: 185000.50,
          saldoNegativo: -12000.75,
          numClientes: 2,
        },
        {
          id: 7,
          nome: 'Rafael Alves',
          cpf: '78901234567',
          email: 'rafael.alves@empresa.com',
          telefone: '41990000007',
          saldoPositivo: 160000.30,
          saldoNegativo: -11000.00,
          numClientes: 23,
        },
        {
          id: 8,
          nome: 'Patrícia Gomes',
          cpf: '89012345678',
          email: 'patricia.gomes@empresa.com',
          telefone: '41990000008',
          saldoPositivo: 140000.00,
          saldoNegativo: -9000.00,
          numClientes: 63,
        },
        {
          id: 2,
          nome: 'Ana Costa',
          cpf: '23456789012',
          email: 'ana.costa@empresa.com',
          telefone: '41990000002',
          saldoPositivo: 120000.00,
          saldoNegativo: -8000.00,
          numClientes: 89,
        },
        {
          id: 10,
          nome: 'Juliana Martins',
          cpf: '01234567890',
          email: 'juliana.martins@empresa.com',
          telefone: '41990000010',
          saldoPositivo: 110000.00,
          saldoNegativo: -6000.00,
          numClientes: 61,
        },
        {
          id: 4,
          nome: 'Mariana Souza',
          cpf: '45678901234',
          email: 'mariana.souza@empresa.com',
          telefone: '41990000004',
          saldoPositivo: 98000.40,
          saldoNegativo: -5000.10,
          numClientes: 49,
        },
        {
          id: 6,
          nome: 'Fernanda Lima',
          cpf: '67890123456',
          email: 'fernanda.lima@empresa.com',
          telefone: '41990000006',
          saldoPositivo: 75000.90,
          saldoNegativo: -3000.50,
          numClientes: 39,
        }
      ];

      localStorage.setItem(this.storageKey, JSON.stringify(gerentes));
    }
  }

  private getData(): any[] {
    const data = localStorage.getItem(this.storageKey);
    return data ? JSON.parse(data) : [];
  }

  private setData(gerentes: any[]): void {
    localStorage.setItem(this.storageKey, JSON.stringify(gerentes));
  }

  // GET
  get(): any[] {
    return this.getData();
  }

  // POST
  post(gerente: any): void {
    const gerentes = this.getData();
    gerente.id = Date.now();
    gerentes.push(gerente);
    this.setData(gerentes);
  }

  // PUT (por id)
  put(id: number, gerenteAtualizado: any): void {
    const gerentes = this.getData();
    const index = gerentes.findIndex(a => a.id === id);

    if (index !== -1) {
      gerentes[index] = gerentes;
      this.setData(gerentes);
    }
  }

  // DELETE (por id)
  delete(id: number): void {
    const gerentes = this.getData();
    const filtrados = gerentes.filter(a => a.id !== id);
    this.setData(filtrados);
  }


}
