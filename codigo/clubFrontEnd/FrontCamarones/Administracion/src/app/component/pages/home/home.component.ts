import { Component, computed, signal } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { LoginComponent } from '../../auth/login/login.component';
import { FooterComponent } from '../../shared/footer/footer.component';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import{MatToolbarModule} from '@angular/material/toolbar'
import { SidenavComponent } from '../../shared/sidenav/sidenav.component';
import { CuotaComponent } from '../cuota/cuota.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { LoginDialogComponent } from '../../shared/login-dialog/login-dialog.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  imports:[
    RouterModule,
    LoginComponent,
    FooterComponent,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    SidenavComponent,
    CuotaComponent,
    MatDialogModule,
    LoginDialogComponent
  ]
})
export class HomeComponent {
  constructor(private router:Router, private dialog:MatDialog) {
    const usuarioHaIniciadoSesion = true;


    if (!usuarioHaIniciadoSesion){
  
        this.openLoginDialog()
    }
  }


  collapsed =signal(false)
  
  sidenavWidth= computed(()=> this.collapsed()? '250px': '60px');

  clearRouterOutlet() {
    this.router.navigate(['/']);
  }




  openLoginDialog() {
    console.log('hola')
    // Abrir el diálogo de inicio de sesión
    const dialogRef = this.dialog.open(LoginDialogComponent, {
      width: '300px', // Ajusta el ancho según tus necesidades
      disableClose: true, // Impide cerrar el diálogo haciendo clic fuera de él
    });

    // Puedes suscribirte a eventos de dialogRef si es necesario
    dialogRef.afterClosed().subscribe((result) => {
      // Lógica después de cerrar el diálogo (si es necesario)
    });
  }
}
