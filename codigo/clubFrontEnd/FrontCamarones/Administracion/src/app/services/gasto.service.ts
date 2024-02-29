import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Gasto } from '../models/Gasto';
import { Proveedor } from '../models/Proveedor';


@Injectable({
    providedIn: 'root'
  })
  
export class GastoService {

    private url ='http://localhost:8080/gasto'
    constructor(private http:HttpClient){}

    getAll():Observable<any>{
        return this.http.get(this.url)
    }

    add(gasto: Gasto): Observable<any>{
        return this.http.post(this.url,gasto)
    }

    delete(gastoId: Number): Observable<any>{
        return this.http.delete(this.url + '/' + gastoId + '/eliminar')
    }

    Update(gastoId: number, GastoUpdate: Gasto): Observable<any>{
        return this.http.put(this.url+ '/' + gastoId + '/actualizar', GastoUpdate)
    }
 
    gastoXFecha (fecha: Date): Observable<any>{
        return this.http.get(this.url+'/'+ fecha +'/porFecha')
    }

    gastoXMes (mes: string): Observable<any>{
        console.log(mes)
        return this.http.get(this.url+ '/'+ mes +'/porMes')
    }
//sin usar
    gastoXFechaYProveedor(fecha: string, proovedor: Proveedor): Observable<any>{
        const params = new HttpParams()
            .set('fecha',fecha)
            .set('idProveedor',proovedor.id);

        return this.http.get(this.url+ '/porFechaYProveedor',{params})
    }

    gastoEntreFechas(fechaInicio: string, fechaFinal: string):Observable<any>{
        const params = new HttpParams()
            .set('fechaInicio',fechaInicio)
            .set('fechaFinal',fechaFinal)

        return this.http.get(this.url + '/entreFechas',{params})
    }

    gastoTotalXProveedor(proveedor:Proveedor): Observable<any>{
        return this.http.get(this.url+'/'+ proveedor + '/totalPorProveedor' )
    }
}