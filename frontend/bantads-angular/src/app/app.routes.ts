import { Routes } from '@angular/router';

import { 
    Login, Cadastro, 
    CustomerDashboard, ProfileChange, BankStatementLookup,
    ManagerDashboard, ClientList, ClientSearch, Top3Clients,
    AdministratorDashboard, Gerentes, RelatorioDeClientes
} from './pages';

import { MainLayout } from './layout';

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
            { path: "cliente/extrato", component: BankStatementLookup },

            //Painel "Gerente"
            { path: "gerente", component: ManagerDashboard },
            { path: "gerente/clientes", component: ClientList },
            { path: "gerente/clientes/buscar", component: ClientSearch },
            { path: "gerente/clientes/top3", component: Top3Clients },

            //Painel "Administrador"
            { path: "admin", component: AdministratorDashboard },
            { path: "admin/gerentes", component: Gerentes },
            { path: "admin/relatorio", component: RelatorioDeClientes }
        ]
    }
];
