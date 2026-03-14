import { Routes } from '@angular/router';
import { Gerentes } from './pages';
import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';

export const routes: Routes = [
    //Telas iniciais
    //OBS.: Apenas deixei como Gerentes pois ainda não foram criadas as páginas.
    { path: "", component: Gerentes },
    { path: "cadastrar", component: Gerentes },
    
    //Painel "Cliente"
    {
        path:"costumerDashboard",
        component: CustomerDashboard
    },

    //Painel "Gerente"

    //Painel "Administrador"
    { path: "admin/gerentes", component: Gerentes }
];