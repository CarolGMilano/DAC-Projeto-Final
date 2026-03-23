import { Routes } from '@angular/router';
<<<<<<< Updated upstream
import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';
=======

import { Login, Cadastro, Gerentes } from './pages';
import { MainLayout } from './layout';

import { CustomerDashboard } from './pages/cliente/customer-dashboard/customer-dashboard'
import { ProfileChange } from './pages/cliente/profile-change/profile-change';
import { BankStatementLookup } from './components/bank-statement-lookup/bank-statement-lookup';
import { ClientSearch } from './components/client-search/client-search'; 
import { ClientList } from './components/client-list/client-list'
import { AdministratorDashboard } from './components/administrator-dashboard/administrator-dashboard';
import { ManagerDashboard } from './components/manager-dashboard/manager-dashboard';
import { RelatorioDeClientes } from './pages/relatorio-de-clientes/relatorio-de-clientes';
>>>>>>> Stashed changes

export const routes: Routes = [
    {
        path:"costumerDashboard",
        component: CustomerDashboard
    }
];