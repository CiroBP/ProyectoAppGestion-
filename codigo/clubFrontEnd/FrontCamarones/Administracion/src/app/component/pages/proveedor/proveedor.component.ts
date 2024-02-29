import { Component, OnInit } from '@angular/core';
import { Proveedor } from '../../../models/Proveedor';
import { ProveedorService } from '../../../services/proveedor.service';
import { FormsModule, FormGroup, Validators,FormBuilder,ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-proveedor',
  standalone:true,
  templateUrl: './proveedor.component.html',
  styleUrl: './proveedor.component.css',
  imports:[FormsModule,
    ReactiveFormsModule,
    RouterLink,
    CommonModule
  ],
})
export class ProveedorComponent  implements OnInit{
  listaProveedores= new Array<Proveedor>();
  public form: FormGroup;
  editingMode: boolean = false;
  proveedor: Proveedor = new Proveedor();
  editingProvId: number =null;
  

  constructor(private proveedorService: ProveedorService, private fb:FormBuilder) {
    this.form = this.fb.group({
      Nombre: ['', Validators.required],
      Categoria: ['', Validators.required],
      gastos: ['']
    });
  }


  ngOnInit(): void {
    this.getAll();

  }

  get Nombre(){return this.form.get('Nombre')}
  get Categoria(){return this.form.get('Categoria')}

  private createProveedor(proveedor:Proveedor){
    this.proveedorService.add(proveedor).subscribe(()=>{
      alert('Alta Exitosa');
      this.resetForm();
      this.getAll();
    },
    error =>{
      alert(`Error: ${error.error.message}`);
      console.error(error);
    }
    );
  }
  private updateProveedor(proveedor:Proveedor){
    this.proveedorService.Update(this.editingProvId!,proveedor).subscribe(()=>{
      alert('Actualización exitosa');
      this.resetForm();
      this.getAll();
    },
    error => {
      alert(`Error: ${error.error.message}`);
      console.error(error);
    }
    )
  }

  addProv(){
    const proveedor = this.createProvFromForm();
    if(this.editingMode){
      this.updateProveedor(proveedor);
    }else {
      this.createProveedor(proveedor);
    }
  }

  editProv(proveedor: Proveedor){
    console.log(proveedor);
    this.form.patchValue({
      Nombre: proveedor.nombre,
      Categoria: proveedor.categoria,
    });
    this.editingProvId = proveedor.id
    this.editingMode = true;
  }

  private createProvFromForm(): Proveedor{
    const proveedor= new Proveedor();
    proveedor.nombre = this.Nombre.value;
    proveedor.categoria = this.Categoria.value;
    console.log(proveedor)
    return proveedor;
  }
  getAll(){
    this.proveedorService.getAll().subscribe(response =>{
      console.log(response)
      this.listaProveedores= response;
    }, error =>{
      console.error(error);
      alert("Error: " + error.error.message);
    }
    )

  }



  resetForm() {
    this.form.reset();
    this.editingMode = false;
    this.editingProvId = null;
  }

  delete (ProvId){
    this.proveedorService.delete(ProvId).subscribe(()=>{
      alert("Baja Exitosa!")
      this.getAll();
    }, error=>{
      console.error(error);
      alert("Error:" + error.error.message)
    }
    
    )
  }


}


