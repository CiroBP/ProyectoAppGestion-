import { Component, Input, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { signal } from '@angular/core';
import { routes } from '../../../app-routing.module';
import { MatListModule } from '@angular/material/list'
import { MatIconModule} from '@angular/material/icon'
import { RouterLink, RouterLinkActive } from '@angular/router';

export type MenuItem={
  icon: string;
  label:string;
  route?
}

@Component({
  selector: 'app-sidenav',
  standalone: true,
  imports: [
    CommonModule,
    MatListModule,
    MatIconModule,
    RouterLink
  ],
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.css'
})
export class SidenavComponent {


  sidenavCollapsed = signal(false);
  @Input() set collapsed(val: boolean){
    this.sidenavCollapsed.set(val);
  }

  menuItem = signal<MenuItem[]>([
    {
      icon: 'contacts',
      label: 'Socio',
      route: 'socio',
    },
    {
      icon: 'account_balance',
      label: 'Cuota',
      route: 'cuota',
    },
    {
      icon: 'assessment',
      label: 'Gasto',
      route: 'gasto',
    },
    {
      icon: 'assignment_ind',
      label: 'Operador',
      route: 'operador',
    },
    {
      icon: 'room_services',
      label: 'Proveedor',
      route: 'proveedor',
    },
    {
      icon: 'home',
      label: 'Home',
      route: 'dashboard',
    },
  ])

  profilePicSize= computed(()=> this.sidenavCollapsed()? '100':'32');
}
