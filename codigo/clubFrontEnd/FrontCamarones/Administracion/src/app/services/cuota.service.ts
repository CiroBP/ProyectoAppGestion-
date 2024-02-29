import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cuota } from '../models/Cuota';


@Injectable({
    providedIn: 'root'
  })
  
  export class CuotaService {
    private url ='http://localhost:8080/cuota'
    constructor(private http:HttpClient){}

    getAll(): Observable<any>{
        return this.http.get(this.url)
    }

    delete(cuotaId: Number): Observable<any>{
        return this.http.delete(this.url+ '/' + cuotaId +'/eliminar')
    }

    add(cuota: Cuota): Observable<any>{
        return this.http.post(this.url, cuota)
    }

    update(cuotaId: number, CuotaUpdate: Cuota): Observable<any>{
      return this.http.put(this.url+ '/' + cuotaId +'/actualizar', CuotaUpdate)
    }

    pagarCuota(monto: number, cuota: Cuota): Observable<any>{
      console.log(monto)
      console.log(cuota)
      return this.http.put(this.url + '/' + monto + '/pay', cuota)
    }

  }