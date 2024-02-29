import { Routes , RouterModule } from "@angular/router";
import { HomeComponent } from "./component/pages/home/home.component";
import { LoginComponent } from "./component/auth/login/login.component";
import { CuotaComponent } from "./component/pages/cuota/cuota.component";
import { GastoComponent } from "./component/pages/gasto/gasto.component";
import { OperadorComponent } from "./component/pages/operador/operador.component";
import { ProveedorComponent } from "./component/pages/proveedor/proveedor.component";
import { SocioComponent } from "./component/pages/socio/socio.component";
import { DashboardComponent } from "./component/pages/dashboard/dashboard.component";
import { LoginDialogComponent } from "./component/shared/login-dialog/login-dialog.component";



export const routes: Routes= [
    {
        path:'',
        pathMatch:'full',
        redirectTo:'dashboard'
    },
    {
        path:'dashboard',
        component: DashboardComponent,
    },
    {
        path:'login',
        component: LoginDialogComponent,
    },
    {
        path:'cuota',
        component: CuotaComponent,
    },
    {
        path:'gasto',
        component: GastoComponent,
    },
    {
        path:'operador',
        component: OperadorComponent,
    },
    {
        path:'proveedor',
        component: ProveedorComponent,
    },
    {
        path:'socio',
        component: SocioComponent,
    }
];
