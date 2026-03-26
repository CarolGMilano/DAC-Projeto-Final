import { Component } from '@angular/core';
import { ClienteService } from '../../services';
import { ICliente, SharedModule } from '../../shared';
import { FormsModule } from '@angular/forms';
import { MoedaBrPipe } from '../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-client-list',
  imports: [FormsModule, MoedaBrPipe, SharedModule],
  templateUrl: './client-list.html',
  styleUrl: './client-list.css',
})
 //R12: Consultar Todos os Clientes - Deve apresentar em uma tabela todos os seus clientes, contendo CPF, Nome, Cidade, Estado, Saldo da conta, Limite da conta. Deve ser ordenado de forma crescente por Nome. Deve ser disponibilizado um campo de texto onde o gerente pode pesquisar o cliente por CPF (ou parte dele) e Nome (ou parte dele). Cada cliente deve possuir um link que, ao ser pressionado, vai para uma tela contendo todos os dados do cliente e de sua conta;
export class ClientList {
  clientes: ICliente[] = [];
  filtro: string = '';

  mostrarModal: boolean = false;
  clienteSelecionado?: ICliente;

  constructor(private clienteService: ClienteService) {}

  ngOnInit() {
    this.clientes = this.clienteService.get()
      .sort((a, b) => a.nome.localeCompare(b.nome));
  }

  buscar() {
    const todos = this.clienteService.get()
      .sort((a, b) => a.nome.localeCompare(b.nome));

    this.clientes = todos.filter(c =>
      c.nome.toLowerCase().includes(this.filtro.toLowerCase()) ||
      c.cpf.includes(this.filtro)
    );
  }

  verDetalhes(cliente: ICliente) {
    this.clienteSelecionado = cliente;
    this.mostrarModal = true;
  }

  fecharModal() {
    this.mostrarModal = false;
    this.clienteSelecionado = undefined;
  }
}
