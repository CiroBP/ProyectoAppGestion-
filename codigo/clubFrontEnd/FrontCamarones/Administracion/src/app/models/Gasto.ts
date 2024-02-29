
import {Proveedor} from "./Proveedor"

export class Gasto {
    id: number;
    nombreGasto: string;
    fecha: Date;
    montoGasto: number;
    metodoPago: string;
    detalleGasto: string;
    proveedor: Proveedor;
    nombreProveedor: string;
}