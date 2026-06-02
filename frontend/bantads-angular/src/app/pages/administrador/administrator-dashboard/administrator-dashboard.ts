import { Component, inject } from '@angular/core';
import { GerenteService } from '../../../services';
import { IDashboardAdminResponse, IGerente, TipoUsuario } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-administrator-dashboard',
  imports: [MoedaBrPipe],
  templateUrl: './administrator-dashboard.html',
  styleUrl: './administrator-dashboard.css',
})
export class AdministratorDashboard {
  private gerenteService = inject(GerenteService);
  
  lista?: IDashboardAdminResponse[];

  ngOnInit() {
   this.listarTodos();
  }

  listarTodos() {
    this.gerenteService.listarTodosDashboard().subscribe({
      next: (lista) => {
        this.lista = (lista ?? [])
          .filter(item => item?.gerente?.tipo !== TipoUsuario.ADMINISTRADOR)
          .sort((a, b) => (b.saldo_positivo ?? 0) - (a.saldo_positivo ?? 0));
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

}
