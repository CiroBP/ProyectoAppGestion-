import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone:true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports:[FormsModule,ReactiveFormsModule]
})
export class LoginComponent {

  constructor(private router: Router, private cdRef: ChangeDetectorRef){}

  public Datos:FormGroup

  login(){
    const loginExitoso =true;
    // aca se aplica la logica para el incio de sesion y si es exitosa


    if(loginExitoso){
      this.router.navigate(['home'])
      this.cdRef.detectChanges();
  }
  }

}
