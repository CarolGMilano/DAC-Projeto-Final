import { Component } from '@angular/core';
import { ClienteService } from '../../services';
import { ICliente } from '../../shared';

@Component({
  selector: 'app-client-search',
  imports: [],
  templateUrl: './client-search.html',
  styleUrl: './client-search.css',
})
//R13: Consultar Cliente - Em uma tela em branco, o gerente deve informar em um campo de texto o CPF, o sistema deve mostrar todos os dados do cliente, incluindo os dados de sua conta (saldo e limite);
//coloquei qual gerente para facilitar testes futuros.
export class ClientSearch {
  cliente!: ICliente;
  cpf: string = '52998224725'
  constructor(
    private clienteService: ClienteService,
  
  ) {}

  ngOnInit() {
    this.cliente = this.clienteService.getByCPF(this.cpf);
  }
}
