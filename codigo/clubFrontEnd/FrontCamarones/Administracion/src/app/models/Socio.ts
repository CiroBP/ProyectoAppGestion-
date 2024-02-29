import { Cuota } from "./Cuota";

export class Socio{
    id: number;
    nombre: string;
    apellido: string;
    contacto: string;
    fechaNacimiento: Date;
    descuentoCuota: number;
    categoria: string;
    diciplina:string;
    cuotas: [Cuota]
}