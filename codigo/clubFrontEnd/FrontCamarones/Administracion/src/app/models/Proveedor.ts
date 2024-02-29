import { Gasto } from "./Gasto";

export class Proveedor{
    id: number;
    nombre: string;
    categoria: string;
    gastos: [Gasto]
}