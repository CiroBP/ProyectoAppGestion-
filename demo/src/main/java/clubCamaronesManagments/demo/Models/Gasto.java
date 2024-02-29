package clubCamaronesManagments.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gasto {
    @Id
    private Integer id;
    @NotNull
    private String nombreGasto;
    @NotNull
    private Date fecha;
    @NotNull
    private double montoGasto;
    @NotNull
    private String metodoPago;
    @NotNull
    private String detalleGasto;
    @ManyToOne
    @JoinColumn(name ="proveedor_id")
    @JsonBackReference
    private Proveedor proveedor;
}