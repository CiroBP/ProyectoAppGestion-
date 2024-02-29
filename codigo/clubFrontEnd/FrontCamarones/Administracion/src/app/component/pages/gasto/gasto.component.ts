import { AsyncPipe, CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder,FormControl,FormGroup,FormsModule,ReactiveFormsModule,Validators } from '@angular/forms';
import { Gasto } from '../../../models/Gasto';
import { GastoService } from '../../../services/gasto.service';
import { ProveedorService } from '../../../services/proveedor.service';
import { Proveedor } from '../../../models/Proveedor';
import { isDataSource } from '@angular/cdk/collections';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { Data } from '@angular/router';

@Component({
  selector: 'app-gasto',
  standalone:true,
  imports:[
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatInputModule,
    MatFormFieldModule,
    MatAutocompleteModule,
    AsyncPipe
  ],
  templateUrl: './gasto.component.html',
  styleUrl: './gasto.component.css'
})
export class GastoComponent implements OnInit{
  // Variables Globales
  listaGasto = new Array<Gasto>();
  editingMode: boolean = false;
  public form:FormGroup;
  editingGastoId: number =null;
  gasto: Gasto = new Gasto
  proveedores = new Array<Proveedor>();


  modoBusqueda: Boolean = false;
  public formBusqueda: FormGroup;
  fechaSeleccionada: Date = null

  
  constructor(private gastoService: GastoService,private fb: FormBuilder, private proveedorService: ProveedorService ){
    this.proveedoresFiltrados = this.proveedores.slice();
    this.form = this.fb.group({
      NombreGasto:['',Validators.required],
      Fecha: ['',Validators.required],
      MontoGasto: ['',Validators.required],
      MetodoPago: ['',Validators.required],
      DetallePago: ['',Validators.required],
      Proveedor: ['',Validators.required]
    })

    this.formBusqueda = this.fb.group({
      fechaBusqueda: ['',Validators.required],
      mesSeleccionado: ['',Validators.required],
      fechaProveedor: ['', Validators.required],
      fechaInicio:['', Validators.required],
      fechaFin:['', Validators.required]
    });


  

  }
  get NombreGasto(){return this.form.get('NombreGasto')}
  get Fecha(){return this.form.get('Fecha')}
  get MontoGasto(){return this.form.get('MontoGasto')}
  get MetodoPago(){return this.form.get('MetodoPago')}
  get DetallePago(){return this.form.get('DetallePago')}
  get Proveedor(){return this.form.get('Proveedor')}

  get FechaBusqueda(){return this.formBusqueda.get('fechaBusqueda')}
  get mesSeleccionado(){return this.formBusqueda.get('mesSeleccionado')}

  get fechaProveedor(){return this.formSeleccionProveedor.get('fechaProveedor')}

  private createGastoFromForm(): Gasto{
    const gasto = new Gasto();
    gasto.nombreGasto = this.NombreGasto.value;
    gasto.fecha = this.Fecha.value;
    gasto.montoGasto = this.MontoGasto.value;
    gasto.metodoPago = this.MetodoPago.value;
    gasto.detalleGasto = this.DetallePago.value;

    const proveedorId = this.Proveedor.value;
    const proveedorSeleccionado = this.proveedores.find(proveedor => proveedor.id == proveedorId)

    gasto.proveedor = proveedorSeleccionado;
    console.log(gasto)
    return gasto;
  }

  private createGasto(gasto:Gasto){
    this.gastoService.add(gasto).subscribe(()=>{
      alert('Alta Exitosa');
      this.resetForm();
      this.getAll();
    },
    error => {
      alert(`Error: ${error.error.message}`);
      console.error(error);
    }
    );
  }

  private updateGasto(gasto:Gasto){
    this.gastoService.Update(this.editingGastoId!, gasto).subscribe(()=>{
      alert('Actualización exitosa');
      this.resetForm();
      this.getAll();
    },
    error => {
      alert(`Error: ${error.error.message}`);
      console.error(error);
    }
    );
  }


 /* codigo para poder detectar a los proveedores */
  obtenerProveedores() {

    this.proveedorService.getAll().subscribe(
      (data) => {

        this.proveedores = data;
        this.enlazarGastosConProveedores();
      },
      (error) => {
        console.error('Error al obtener proveedores', error);
      }
    );
  }

  enlazarGastosConProveedores() {
    this.listaGasto.forEach((gasto) => {
      this.proveedores.forEach((proveedor) => {
        if (proveedor.gastos.some((g) => g.id === gasto.id)) {
          gasto.proveedor = proveedor;
        }
      });
    });
  }

/* codigo que se inicia el principio*/ 
  ngOnInit(): void {
    this.obtenerProveedores();
    this.getAll();
  }

  getAll(){
    this.gastoService.getAll().subscribe(response=>{

      this.listaGasto= response;
      this.obtenerProveedores()

    }, error =>{
      console.error(error)
      alert("Error: " + error.error.message)
    }
    )
  }
/* Agrega un gasto */
  addGasto(){
    const gasto = this.createGastoFromForm();

    if(this.editingMode){
      this.updateGasto(gasto)
    }else{
      this.createGasto(gasto)
    }
  }
/* edita un gasto*/
  editGasto(gasto:Gasto){
    this.form.patchValue({
      NombreGasto: gasto.nombreGasto,
      Fecha: gasto.fecha,
      MontoGasto: gasto.montoGasto,
      MetodoPago: gasto.metodoPago,
      DetallePago: gasto.detalleGasto,
      Proveedor: gasto.nombreProveedor
    })

    this.editingGastoId = gasto.id;
    this.editingMode = true;
  }
/*muestra un alert de los detalles */
  mostrarDetalles(detalles: string): void {
    
    window.alert('Detalles del Gasto:\n' + detalles);
}
/*borra un gasto */
  delete(gastoId){
    this.gastoService.delete(gastoId).subscribe(()=>{
      alert("Baja Exitosa!")
      this.getAll();
    },
    error =>{
      console.error(error);
      alert("Error:" + error.error.message)
    }
    )
  }

  /*Ayudas de forms  */
  resetForm() {
    this.form.reset();
    this.editingMode = false;
    this.editingGastoId = null;
  }
  activarModoBusqueda(){
    this.modoBusqueda= true
  }
  desactivarModoBusqueda() {
    this.modoBusqueda = false;
    this.getAll()
  }



/*Funcion para buscar por fecha*/


  buscarGastoXFecha(){
      const fechaBusquedaControl = this.formBusqueda.get('fechaBusqueda');
      if (fechaBusquedaControl) {
        this.fechaSeleccionada = fechaBusquedaControl.value;
        console.log(fechaBusquedaControl.value);
        this.obtenerGastoPorFecha(this.fechaSeleccionada);
    }
  }

  obtenerGastoPorFecha(fecha:Date){
    this.gastoService.gastoXFecha(fecha).subscribe(
      (data) => {
        this.listaGasto = data; // Asigna los resultados a la propiedad
      },
      (error) => {
        console.error('Error al obtener gastos por fecha', error);
      }
    );
  }


  /*Funcion para buscar por mes*/


  buscarGastoXMes() {
    const nombreMes = this.formatearMes(this.mesSeleccionado.value);
    
    // Utiliza la función formatearMes

    this.gastoService.gastoXMes(nombreMes).subscribe(
      (data) => {
        this.listaGasto = data;
      },
      (error) => {
        console.error('Error al obtener gastos por mes', error);
      }
    );
  }

  formatearMes(mes: string): string {
    // Dividir la cadena "yyyy-mm" en año y mes
    const [ano, mesStr] = mes.split('-');

    // Crear una nueva fecha con el año y el mes (restando 1)
    const fecha = new Date(parseInt(ano), parseInt(mesStr) - 1, 1);

    // Formatear el mes en el formato deseado
    const options: Intl.DateTimeFormatOptions = { month: 'long' as const };
    return new Intl.DateTimeFormat('es-AR', options).format(fecha).toLowerCase();
  }



/*Funcion para buscar por fecha y proveedor */

@ViewChild('input') input: ElementRef<HTMLInputElement>;
  proveedorSeleccionado: Proveedor
  proveedoresFiltrados: Proveedor[];
  valorInput:Proveedor;
  ProveedorXFecha: string;
  formSeleccionProveedor= new FormControl();


  filtrarProveedor(event: any) {
    const filtro = event.target.value.toLowerCase();
    this.proveedoresFiltrados = this.proveedores.filter(
      proveedor =>
        proveedor.nombre.toLowerCase().includes(filtro) ||
        proveedor.categoria.toLowerCase().includes(filtro)
    );
    if (!filtro) {
      this.proveedoresFiltrados = this.proveedores.slice();
    }
  }

  seleccionarProveedor(proveedor: Proveedor) {
    this.valorInput = this.formSeleccionProveedor.value
    this.formSeleccionProveedor.setValue(proveedor.nombre +' '+ proveedor.categoria)
    this.proveedorSeleccionado =proveedor
  }

  fechaProveedorSelecionada(){
    const fecha =  this.formBusqueda.get('fechaProveedor')
    if(fecha){
      this.ProveedorXFecha = fecha.value
      this.buscarGastoXProveedorYFecha(this.ProveedorXFecha)
    }
  }

  buscarGastoXProveedorYFecha(fecha:string) {
    const Proveedor= this.valorInput
    console.log(Proveedor);
    console.log(fecha);
    this.gastoService.gastoXFechaYProveedor(fecha,Proveedor).subscribe((data)=> {
      console.log(data)
      this.listaGasto = data
    },
    (error) => {
      console.error('Error al obtener gastos por mes', error);
      alert('No se encontraron proveedores')
    })

    // Realizar la búsqueda de gastos...
  }

/*Funcion para buscar entre fechas*/ 

  gastoInicio: string
  gastoFin:string

  EntreFechas(){
    const Inicio =  this.formBusqueda.get('fechaInicio')
    const Fin =  this.formBusqueda.get('fechaFin')
    console.log(typeof Inicio , typeof Fin)
    if(Inicio && Fin){
      this.gastoInicio = Inicio.value
      this.gastoFin = Fin.value
      
      this.BuscarGastoEntreFechas(this.gastoInicio,this.gastoFin)

    }
  }

  BuscarGastoEntreFechas(Inicio:string, Fin:string){

    this.gastoService.gastoEntreFechas(Inicio,Fin).subscribe((data)=>{
      this.listaGasto = data
    },
    (error) =>{
      console.error('Error al obtener gastos entre fechas', error);
    })
  }

}
