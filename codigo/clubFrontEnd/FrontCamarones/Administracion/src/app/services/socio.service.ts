import { Injectable } from '@angular/core';
import { Socio } from '../models/Socio';
import { HttpClient, HttpHeaders, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { OperadorService } from './operador.service';



@Injectable({
    providedIn: 'root'
  })

export class socioService{



  private url ='http://localhost:8080/socio'
constructor(private http:HttpClient){}
    
add(socio: Socio): Observable<any>{
  return this.http.post(this.url ,socio);
}

getAll(): Observable<any>{
  return this.http.get(this.url)
}

delete(SocioId: Number): Observable<any>{
  return this.http.delete(this.url +'/' + SocioId +'/eliminar')
}

Update(SocioId: number, SocioUpdate: Socio): Observable<any>{
  return this.http.put(this.url + '/' + SocioId + '/actualizar' , SocioUpdate)
}

BuscarXDiciplina(diciplina:String): Observable<any>{
  return this.http.get(this.url + '/' + diciplina +'/bydiciplina' )
}

BuscarXCategoria(categoria:String): Observable<any>{
  return this.http.get(this.url+'/'+ categoria + '/Categoria')
}

ObtenerCutoasSocio(socio:Socio): Observable<any>{
  return this.http.get(this.url+'/'+ socio + 'obtenerCuota')
}

}