package clubCamaronesManagments.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuota {

    @Id
    private Integer id;
    @NotNull
    private String mesCuota;
    @NotNull
    private Date fechaVencimiento;
    @NotNull
    private boolean pagada;
    @NotNull
    private double cantidadPagada;
    @ManyToOne
    @JoinColumn(name = "socio_id")
    @JsonBackReference
    private Socio socio;
    @OneToOne(mappedBy = "cuota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private PrecioCuota precioCuota;

}
