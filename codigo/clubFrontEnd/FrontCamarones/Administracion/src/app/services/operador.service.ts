import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

  
  export class OperadorService {
    
    private url ='http://localhost:8080/operador'
    constructor(private http:HttpClient){}
/*
    login(username:string,password:string):Observable<any>{
      return true

    }
    */
  }