package clubCamaronesManagments.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operador {

    @Id
    private Integer id;
    @NotNull
    private String userName;
    @NotNull
    private String contrasenia;
    @NotNull
    private boolean permisoTotal;
    @NotNull
    private String contacto;
}

