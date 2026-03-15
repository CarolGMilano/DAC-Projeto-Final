import { Routes } from '@angular/router';
import { Gerentes } from './pages'
import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';
import {ProfileChange} from './pages/cliente/profile-change/profile-change'
export const routes: Routes = [
    //Telas iniciais
    //OBS.: Apenas deixei como Gerentes pois ainda não foram criadas as páginas.
    { path: "", component: Gerentes },
    { path: "cadastrar", component: Gerentes },
    
    //Painel "Cliente"
    {
        path:"custumerDashboard",
        component: CustomerDashboard
    },

   { 
    path: 'profileChange', 
    component: ProfileChange
  },
    //Painel "Gerente"

    //Painel "Administrador"
    { path: "admin/gerentes", component: Gerentes }
];