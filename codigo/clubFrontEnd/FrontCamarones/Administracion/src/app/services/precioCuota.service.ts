import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PrecioCuota } from '../models/PrecioCuota';




@Injectable({
    providedIn: 'root'
  })

export class PrecioCuotaService{

    private url ='http://localhost:8080/precioCuota'
    constructor(private http:HttpClient){}
    

    getAll():Observable<any>{
        return this.http.get(this.url)
    }


    Update(Id: number, precioCuota: PrecioCuota): Observable<any>{
        return this.http.put(this.url+ '/' + Id + '/update', precioCuota)
    }
}