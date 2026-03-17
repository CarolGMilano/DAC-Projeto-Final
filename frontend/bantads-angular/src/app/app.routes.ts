import { Routes } from '@angular/router';
import { Gerentes } from './pages';
import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';
import { ProfileChange } from './pages/cliente/profile-change/profile-change';
import { BankStatementLookup } from './components/bank-statement-lookup/bank-statement-lookup';
import { ClientSearch } from './components/client-search/client-search'; 
import { ClientList } from './components/client-list/client-list'
export const routes: Routes = [
    // Telas iniciais
    { path: "", component: Gerentes },
    { path: "cadastrar", component: Gerentes },
    
    // Painel "Cliente"
    {
        path: "customerDashboard", 
        component: CustomerDashboard
    },

    // R4: Alteração de Perfil
    { 
        path: 'profileChange', 
        component: ProfileChange 
    },

    // Painel "Administrador"
    { path: "admin/gerentes", component: Gerentes },

    {
        path: "bankStatement", component: BankStatementLookup
    },

    // R12: Lista de clientes
    {
        path: "clientList", component: ClientList
    },
    
    // R13: Buscar cliente específico
    {
        path: "client-search", component: ClientSearch
    },
];