import { Component, inject } from '@angular/core';
import { ClienteService } from '../../../services';
import { IClienteCompletoResponse, IClienteListagemResponse, SharedModule } from '../../../shared';
import { FormsModule } from '@angular/forms';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-client-list',
  imports: [FormsModule, MoedaBrPipe, SharedModule],
  templateUrl: './client-list.html',
  styleUrl: './client-list.css',
})
 //R12: Consultar Todos os Clientes - Deve apresentar em uma tabela todos os seus clientes, contendo CPF, Nome, Cidade, Estado, Saldo da conta, Limite da conta. Deve ser ordenado de forma crescente por Nome. Deve ser disponibilizado um campo de texto onde o gerente pode pesquisar o cliente por CPF (ou parte dele) e Nome (ou parte dele). Cada cliente deve possuir um link que, ao ser pressionado, vai para uma tela contendo todos os dados do cliente e de sua conta;
export class ClientList {
  private clienteService = inject(ClienteService);

  clientes: IClienteListagemResponse[] = [];
  todosClientes: IClienteListagemResponse[] = [];
  filtro: string = '';

  mostrarModal: boolean = false;
  clienteSelecionado?: IClienteCompletoResponse;

  ngOnInit() {
    this.listarClientes();
  }

  filtrar() {
    const termo = this.filtro.toLowerCase();

    this.clientes = this.todosClientes.filter(c =>
      c.nome.toLowerCase().includes(termo) ||
      c.cpf.toLowerCase().includes(termo)
    );
  }

  listarClientes() {
    this.clienteService.listarClientesDoGerente().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
        this.todosClientes = clientes;
      },
      error: (erro) => {
        if (erro.status === 500) {
          alert(`Erro interno: ${erro.error}`);
        } else {
          alert('Erro inesperado ao listar funcionários.');
          console.log(erro);
        }
      }
    });
  }

  verDetalhes(cpf: string) {
    this.mostrarModal = true;

    this.clienteService.buscar(cpf).subscribe({
      next: (cliente) => {
        this.clienteSelecionado = cliente;
      },
      error: (erro) => {
        if (erro.status === 500) {
          alert(`Erro interno: ${erro.error}`);
        } else {
          alert('Erro inesperado ao listar funcionários.');
          console.log(erro);
        }
      }
    });
  }

  fecharModal() {
    this.mostrarModal = false;
    this.clienteSelecionado = undefined;
  }
}
