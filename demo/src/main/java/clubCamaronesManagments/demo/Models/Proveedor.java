package clubCamaronesManagments.demo.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Proveedor {
    @Id
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    private String categoria;
    @OneToMany(mappedBy = "proveedor")
    @JsonManagedReference
    private List<Gasto> gastos;
}