import { Component, OnInit, ViewChild, Input, ElementRef } from '@angular/core';
import { CuotaService } from '../../../services/cuota.service';
import { socioService } from '../../../services/socio.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Socio } from '../../../models/Socio';
import { AsyncPipe, CommonModule } from '@angular/common';
import {MatAutocompleteModule, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormControl } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatDialog} from '@angular/material/dialog'
import { Cuota } from '../../../models/Cuota';
import { Observable } from 'rxjs';
import { PrecioCuota } from '../../../models/PrecioCuota';
import { PrecioCuotaService } from '../../../services/precioCuota.service';

@Component({
  selector: 'app-cuota',
  standalone:true,
  imports:[
  FormsModule,
  CommonModule,
  MatInputModule,
  MatAutocompleteModule,
  MatFormFieldModule,
  ReactiveFormsModule,
  AsyncPipe
  ],
  templateUrl: './cuota.component.html',
  styleUrl: './cuota.component.css'
})

export class CuotaComponent implements OnInit{
  @ViewChild('input') input: ElementRef<HTMLInputElement>;
  miformControl = new FormControl();
  nombreSocio: string = '';
  apellidoSocio: string = '';
  listaSocios = new Array<Socio>();
  socioSeleccionado: Socio = null;

  opcionesFiltradas: Socio[]
  
  

  constructor(private cuotaService:CuotaService, 
    private socioService:socioService, 
    private dialog:MatDialog,
    private precioCuotaService:PrecioCuotaService){
    this.opcionesFiltradas = this.listaSocios.slice();
  }

 ngOnInit(): void {
   this.getAll()
   this.getPrecioCuota()
 }

 getAll(){
  this.socioService.getAll().subscribe(response => {
    this.listaSocios = response;
  }, error => {
    console.error(error);
    alert("Error: " + error.error.message);
  });
 }


 filtrarSocios(event: any) {
  const filtro = event.target.value.toLowerCase();
  this.opcionesFiltradas = this.listaSocios.filter(
    socio =>
      socio.nombre.toLowerCase().includes(filtro) ||
      socio.apellido.toLowerCase().includes(filtro) ||
      socio.diciplina.toLowerCase().includes(filtro)
  );
  if (!filtro) {
    this.opcionesFiltradas = this.listaSocios.slice();
  }
}

 seleccionarSocio(socio : Socio) {
  const valorInput = this.miformControl.value;
  console.log(valorInput);
  this.miformControl.setValue(socio.nombre +' ' + socio.apellido);
  this.socioSeleccionado = socio
}



disciplinaFiltro: string = '';
  filtrarPorDisciplina() {
    if (this.disciplinaFiltro) {
      this.socioService.BuscarXDiciplina(this.disciplinaFiltro).subscribe(response => {
        this.listaSocios = response;
      }, error => {
        console.error(error);
        alert("Error: " + error.error.message);
      });
    } else {
      this.getAll();
    }
  }

  eliminarCuota(cuotaId){
    this.cuotaService.delete(cuotaId).subscribe(()=>{
      alert('Cuota Eliminada Exitosamente')
      this.getAll()
    }, error =>{
      console.error(error);
      alert("Error:" + error.error.message)
    }
    )
  }

  pagarCuota(monto: number, cuota: Cuota): Observable<any> {

    return this.cuotaService.pagarCuota(monto, cuota);
  }

  abrirDialogoPago(cuota: Cuota): void {
    const monto = prompt('Ingrese el monto a pagar:');
    if (monto !== null) {
      const montoNumerico = parseFloat(monto);
      if (!isNaN(montoNumerico) && montoNumerico > 0) {
        this.pagarCuota(montoNumerico, cuota).subscribe(
          (response) => {
            console.log('Cuota pagada exitosamente', response);
            // Actualiza la lista de cuotas u otra lógica necesaria
          },
          (error) => {
            console.error('Error al pagar la cuota', error);
            // Manejo de errores, si es necesario
          }
        );
      } else {
        alert('Por favor, ingrese un monto válido.');
      }
    }
  }

  precioCuota:PrecioCuota
  array = []
  nuevoPrecio: number;
  precioCuotas: PrecioCuota[] = [];

  getPrecioCuota(): void {
    this.precioCuotaService.getAll().subscribe(response => {
      this.precioCuotas = response;
      // Aquí puedes acceder al precio de la primera cuota
      if (this.precioCuotas.length > 0) {
        const precioPrimeraCuota = this.precioCuotas[0].precio;
        console.log('Precio de la primera cuota:', precioPrimeraCuota);
      } else {
        console.log('No hay cuotas disponibles.');
      }
    }, error => {
      console.error(error);
      alert("Error: " + error.error.message);
    });
  }

  cambiarPrecioCuota(): void {
    if (this.precioCuotas.length > 0) {

      const cuotaActual = this.precioCuotas[0];
  
   
      const nuevaCuota: PrecioCuota = {
        id: cuotaActual.id,
        precio: this.nuevoPrecio || cuotaActual.precio, 
        cuota: cuotaActual.cuota 
      };
  

      this.precioCuotaService.Update(cuotaActual.id, nuevaCuota).subscribe(() => {
        console.log('Precio de cuota actualizado correctamente.');

        this.getPrecioCuota();
        this.getAll()
      }, error => {
        console.error('Error al actualizar el precio de la cuota:', error);
        alert("Error: " + error.error.message);
      });
    } else {
      console.error('No hay cuotas disponibles para actualizar.');
      // Manejar el caso en que no hay cuotas disponibles
    }
  }
}




