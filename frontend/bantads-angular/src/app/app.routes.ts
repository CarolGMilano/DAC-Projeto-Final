import { Routes } from '@angular/router';

import { 
    Login, Cadastro, 
    CustomerDashboard, ProfileChange, BankStatementLookup,
    ManagerDashboard, ClientList, ClientSearch, Top3Clients,
    AdministratorDashboard, Gerentes, RelatorioDeClientes
} from './pages';

import { MainLayout } from './layout';
import { authGuard } from './auth/auth-guard';

export const routes: Routes = [
  //Telas iniciais
  { path: "", component: Login, pathMatch: 'full' },
  { path: "cadastro", component: Cadastro },

  {
    path: '',
    component: MainLayout,
    children: [
      //Painel "Cliente"
      { 
        path: "cliente", 
        component: CustomerDashboard,
        canActivate: [authGuard],
        data: {
          role: ['CLIENTE']
        }
      },
      { 
        path: "cliente/perfil", 
        component: ProfileChange,
        canActivate: [authGuard],
        data: {
          role: ['CLIENTE']
        }
      },
      { 
        path: "cliente/depositar", 
        component: CustomerDashboard,
        canActivate: [authGuard],
        data: {
          role: ['CLIENTE']
        }
      },
      { 
        path: "cliente/sacar", 
        component: CustomerDashboard,
        canActivate: [authGuard],
        data: {
          role: ['CLIENTE']
        }
      },
      { 
        path: "cliente/transferir", 
        component: CustomerDashboard,
        canActivate: [authGuard],
        data: {
          role: ['CLIENTE']
        }
      },
      { 
        path: "cliente/extrato", 
        component: BankStatementLookup,
        canActivate: [authGuard],
        data: {
          role: ['CLIENTE']
        }
      },

      //Painel "Gerente"
      { 
        path: "gerente", 
        component: ManagerDashboard,
        canActivate: [authGuard],
        data: {
          role: ['GERENTE']
        }
      },
      { 
        path: "gerente/clientes", 
        component: ClientList,
        canActivate: [authGuard],
        data: {
          role: ['GERENTE']
        }
      },
      { 
        path: "gerente/clientes/buscar", 
        component: ClientSearch,
        canActivate: [authGuard],
        data: {
          role: ['GERENTE']
        }
      },
      { 
        path: "gerente/clientes/top3", 
        component: Top3Clients,
        canActivate: [authGuard],
        data: {
          role: ['GERENTE']
        }
      },

      //Painel "Administrador"
      { 
        path: "admin", 
        component: AdministratorDashboard,
        canActivate: [authGuard],
        data: {
          role: ['ADMINISTRADOR']
        }
      },
      { 
        path: "admin/gerentes", 
        component: Gerentes,
        canActivate: [authGuard],
        data: {
          role: ['ADMINISTRADOR']
        }
      },
      { 
        path: "admin/relatorio", 
        component: RelatorioDeClientes,
        canActivate: [authGuard],
        data: {
          role: ['ADMINISTRADOR']
        }
      },
    ]
  },

//Rota coringa: Se tentar acessar alguma rota que não exista, redireciona para a raiz.
  { path: '**', redirectTo: '' }
];