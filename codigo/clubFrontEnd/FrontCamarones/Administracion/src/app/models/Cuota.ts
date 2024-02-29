import { PrecioCuota } from "./PrecioCuota";
import { Socio } from "./Socio";

export class Cuota{
    id: number;
    mesCuota: string;
    fechaVencimiento: Date;
    pagada: boolean;
    cantidadPagada: number;
    precioCuota: PrecioCuota;
    socio: Socio
}

