import { Component } from '@angular/core';
import { ClienteService } from '../../services';
import { ICliente } from '../../shared';

@Component({
  selector: 'app-top3-clients',
  imports: [],
  templateUrl: './top3-clients.html',
  styleUrl: './top3-clients.css',
})

// R14: Consultar 3 melhores clientes
// Deve ser apresentada uma tela contendo somente os clientes que possuem os 3 maiores saldos em conta
// (de qualquer gerente), mostrando CPF, Nome, Cidade, Estado, Saldo da conta,
// ordenado de forma decrescente por saldo;


export class Top3Clients {
  clientes!: ICliente[]; 

  constructor(
    private clienteService: ClienteService,

  ) {}

  ngOnInit() {
    this.clientes = this.clienteService.getTop3();
    console.log(this.clientes);
        
  }
}
