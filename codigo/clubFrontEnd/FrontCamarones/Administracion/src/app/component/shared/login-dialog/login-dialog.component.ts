import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { OperadorService } from '../../../services/operador.service';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-dialog',
  standalone: true,
  imports: [
    FormsModule,
    MatFormField,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    CommonModule

  ],
  templateUrl: './login-dialog.component.html',
  styleUrl: './login-dialog.component.css'
})
export class LoginDialogComponent implements OnInit {
  loginForm: FormGroup;
  loginError: string = '';

  constructor(
    private fb: FormBuilder,
    private operadorService: OperadorService,  
    private dialogRef: MatDialogRef<LoginDialogComponent>
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

    // Método público para exponer dialogRef
    getDialogRef() {
      return this.dialogRef;
    }
  
    // Otros métodos lógicos para el inicio de sesión
  
    closeDialog() {
      this.dialogRef.close();
    }
  

  onSubmit() {
    if (this.loginForm.valid) {
      const username = this.loginForm.get('username').value;
      const password = this.loginForm.get('password').value;

      /*this.operadorService.login(username, password).subscribe(
        (response) => {
          // Si el inicio de sesión es exitoso, cierra el diálogo y realiza otras acciones necesarias.
          this.dialogRef.close();
        },
        (error) => {
          // Si hay un error en el inicio de sesión, muestra un mensaje de error.
          this.loginError = 'Credenciales incorrectas. Por favor, intenta de nuevo.';
        }
      );*/
      if (username == 'a' && password == 'a'){
        this.dialogRef.close()
      }
    }
  }
}
