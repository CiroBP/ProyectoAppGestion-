import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Socio } from '../../../models/Socio';
import { CommonModule } from '@angular/common';
import { socioService } from '../../../services/socio.service';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-socio',
  templateUrl: './socio.component.html',
  styleUrl: './socio.component.css',
  standalone: true,
  imports:[
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    RouterLink,
    DatePipe,
  ]
})
export class SocioComponent implements OnInit {
// inicio de variables globales

  listaSocios = new Array<Socio>();
  editingMode: boolean = false;
  public form:FormGroup;
  editingSocioId: number=null;
  socio: Socio = new Socio();
  selectedDisciplina: string = '';
  selectedCategoria: string = '';
  categoriasRugby: string[] = ['Baby Camarones', 'M-8', 'M-9', 'M-10', 'M-11', 'M-12', 'M-13', 'M-14', 'M-15', 'M-16', 'M-17', 'M-18', 'M-19', 'Plantel Superior', 'Veterano'];
  categoriaHockey: string[] = ['Escuelita','Infantiles','Sub-12','Sub-14','Sub-16','Mayores']
  categorias: string[]
// fin de variables globales

//constructor
  constructor(private fb:FormBuilder, private socioService: socioService){
    this.form = this.fb.group({
      Nombre: ['', Validators.required],
      Apellido: ['', Validators.required],
      Contacto: ['', Validators.required],
      FchNacimiento: ['', Validators.required],
      Diciplina:['', Validators.required],
      categoria: [null]
    });
    
  }
  
// clase de inicio obligatorio
  ngOnInit(): void {

    
    this.getAll();
    this.loadCategoria();
  }
  
  // variables de ayuda del form
  get Apellido(){return this.form.get('Apellido')}
  get Nombre(){return this.form.get('Nombre')}
  get Contacto(){return this.form.get('Contacto')}
  get FchNacimiento(){return this.form.get('FchNacimiento')}
  get Diciplina(){return this.form.get('Diciplina')}
  get categoria(){return this.form.get('categoria')}

 // aca empieza el grupo de mostrado con agregado y modificado
  private createSocioFromForm(): Socio {//Esta FUNCION TIENE UN LOG PARA VERIFICAR SOCIOSSSS
    const socio = new Socio();
    socio.nombre = this.Nombre.value;
    socio.apellido = this.Apellido.value;
    socio.contacto = this.Contacto.value;
    socio.fechaNacimiento = this.FchNacimiento.value;
    socio.diciplina = this.Diciplina.value;
    socio.categoria= this.categoria.value;
    console.log(socio)
    return socio;
  }
  private createSocio(socio: Socio) {
    this.socioService.add(socio).subscribe(
      () => {
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
  private updateSocio(socio: Socio) {
    this.socioService.Update(this.editingSocioId!, socio).subscribe(
      () => {
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
  addSocio() {
    const socio = this.createSocioFromForm();
    
    if (this.editingMode) {
      // Estás en modo de edición, realiza la actualización
      this.updateSocio(socio);
    } else {
      // No estás en modo de edición, agrega un nuevo socio
      this.createSocio(socio);
      }
    }
  editSocio(socio: Socio) {
    console.log(socio);
    this.form.patchValue({
      Nombre: socio.nombre,
      Apellido: socio.apellido,
      Contacto: socio.contacto,
      FchNacimiento: socio.fechaNacimiento,
      Diciplina:socio.diciplina,
      categoria:socio.categoria,
    });
  
    this.editingSocioId = socio.id;
    this.editingMode = true;
  }
  loadCategoria(): void {
    this.form.get('Diciplina').valueChanges.subscribe((disciplina) => {
      if (disciplina === 'Rugby') {
        this.categorias = this.categoriasRugby;
      } else if (disciplina === 'Hockey') {
        this.categorias = this.categoriaHockey;
      } else {
        this.categorias = [];
      }
    });
  
  }
// aca termina
  

// clase para mostrar tabla
getAll() {
  if (this.selectedDisciplina) {
    // Si hay una disciplina seleccionada, realiza la búsqueda por disciplina
    this.socioService.BuscarXDiciplina(this.selectedDisciplina).subscribe(response => {
      this.listaSocios = response;
    }, error => {
      console.error(error);
      alert("Error: " + error.error.message);
    });
  } else if (this.selectedCategoria) {
    // Realizar búsqueda por categoría
    this.socioService.BuscarXCategoria(this.selectedCategoria).subscribe(response => {
      this.listaSocios = response;
    }, error => {
      console.error(error);
      alert("Error: " + error.error.message);
    });
  } else {
    // Si no hay disciplina seleccionada, obtén todos los socios
    this.socioService.getAll().subscribe(response => {
      this.listaSocios = response;
    }, error => {
      console.error(error);
      alert("Error: " + error.error.message);
    });
  }
}

// borrado clase individual
  delete(socioId){
    this.socioService.delete(socioId).subscribe(() =>{
      alert("Baja Exitosa!")
      this.getAll();
    },error =>{
      console.error(error);
      alert("Error:" + error.error.message)
    }
    );

  }

// clase de reseteo de form
  resetForm() {
    this.form.reset();
    this.editingMode = false;
    this.editingSocioId = null;
  }

  

}
