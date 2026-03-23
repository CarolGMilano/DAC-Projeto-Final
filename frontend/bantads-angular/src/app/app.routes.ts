import { Routes } from '@angular/router';

import { Login, Cadastro, Gerentes } from './pages';
import { MainLayout } from './layout';

import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';
import { ProfileChange } from './pages/cliente/profile-change/profile-change';
import { BankStatementLookup } from './components/bank-statement-lookup/bank-statement-lookup';
import { ClientSearch } from './components/client-search/client-search'; 
import { ClientList } from './components/client-list/client-list'
import { AdministratorDashboard } from './components/administrator-dashboard/administrator-dashboard';
import { ManagerDashboard } from './components/manager-dashboard/manager-dashboard';
import { RelatorioDeClientes } from './pages/relatorio-de-clientes/relatorio-de-clientes';
export const routes: Routes = [
    //Telas iniciais
    { path: "", component: Login },
    { path: "cadastro", component: Cadastro },

    {
        path: '',
        component: MainLayout,
        children: [
            //Painel "Cliente"
            { path: "customerDashboard", component: CustomerDashboard },
            { path: "cliente/perfil", component: ProfileChange },
            { path: "cliente/depositar", component: CustomerDashboard },
            { path: "cliente/sacar", component: CustomerDashboard },
            { path: "cliente/transferir", component: CustomerDashboard },
            { path: "cliente/extrato", component: CustomerDashboard },

            //Painel "Gerente"
            { path: "gerente", component: ManagerDashboard },
            { path: "gerente/clientes", component: ClientList },
            { path: "gerente/clientes/buscar", component: ClientSearch },
            { path: "gerente/clientes/top3", component: ClientSearch },

            //Painel "Administrador"
            { path: "admin", component: AdministratorDashboard },
            { path: "admin/gerentes", component: Gerentes },
            { path: "admin/relatorio", component: RelatorioDeClientes },

            { path: "bankStatement", component: BankStatementLookup },
        ]
    }
];]
