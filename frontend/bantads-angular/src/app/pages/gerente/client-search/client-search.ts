import { Component, inject } from '@angular/core';
import { ClienteService } from '../../../services';
import { ICliente,IClienteCompletoResponse,SharedModule } from '../../../shared';
import { FormsModule } from '@angular/forms'; 
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

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
  mensagemErro: string = '';
  cliente?: IClienteCompletoResponse;
  pesquisado: boolean = false; 

  private clienteService = inject(ClienteService)

  buscar() {
    this.pesquisado = true;
    this.mensagemErro = '';
    this.cliente = undefined;

    this.clienteService.buscar(this.cpf).subscribe({
      next: (cliente) => {
        this.cliente = cliente;
      },
      error: (erro) => {
        if (erro.status === 404) {
          this.mensagemErro = erro.error.message;
        } else if (erro.status === 500) {
          this.mensagemErro = erro.error.message;
        } else {
          this.mensagemErro = erro.error.message;
        }
      }
    });
  }
}
