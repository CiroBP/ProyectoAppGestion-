package clubCamaronesManagments.demo.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socio {

    @Id
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    private String contacto;
    @NotNull
    private Date fechaNacimiento;
    @NotNull
    private Integer descuentoCuota;
    @NotNull
    private String categoria;
    @NotNull
    private String diciplina;
    @OneToMany(mappedBy = "socio")
    @JsonManagedReference
    private List<Cuota> cuotas;
}

