import { Component } from '@angular/core';
import { ClienteService } from '../../services';
import { ICliente,SharedModule } from '../../shared';
import { FormsModule } from '@angular/forms'; 
import { MoedaBrPipe } from '../../shared/pipes/moedaBr/moeda-br-pipe';

//R13: Consultar Cliente - Em uma tela em branco, o gerente deve informar em um campo de texto o CPF, o sistema deve mostrar todos os dados do cliente, incluindo os dados de sua conta (saldo e limite);
//coloquei qual gerente para facilitar testes futuros
@Component({
  selector: 'app-client-search',
  templateUrl: './client-search.html',
  styleUrl: './client-search.css',
  standalone: true,
  imports: [FormsModule,SharedModule,MoedaBrPipe] 
})
export class ClientSearch {
  cpf: string = '';
  cliente: ICliente | null = null;
  pesquisado: boolean = false; 

  constructor(private clienteService: ClienteService) {}

  buscar() {
    this.pesquisado = true;
    this.cliente = this.clienteService.getByCPF(this.cpf) || null;
  }
}
