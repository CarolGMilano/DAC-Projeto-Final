import { Routes } from '@angular/router';
import { Gerentes } from './pages';
import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';
import { ProfileChange } from './pages/cliente/profile-change/profile-change';
import { BankStatementLookup } from './components/bank-statement-lookup/bank-statement-lookup';

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
    }
];