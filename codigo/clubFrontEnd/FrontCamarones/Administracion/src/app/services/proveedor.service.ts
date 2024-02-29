import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Proveedor } from '../models/Proveedor';


@Injectable({
    providedIn: 'root'
  })
  
  export class ProveedorService {
    private url ='http://localhost:8080/proveedor'
    constructor(private http:HttpClient){}

    getAll(): Observable<any>{
        return this.http.get(this.url)
    }

    add(proveedor: Proveedor): Observable<any>{
        return this.http.post(this.url ,proveedor);
    }

    delete(proveedorId: Number): Observable<any>{
        return this.http.delete(this.url +'/' + proveedorId +'/eliminar')
    }

    Update(proveedor: number, ProveedorUpdate: Proveedor): Observable<any>{
        return this.http.put(this.url + '/' + proveedor + '/actualizar' , ProveedorUpdate)
    }
    

  }