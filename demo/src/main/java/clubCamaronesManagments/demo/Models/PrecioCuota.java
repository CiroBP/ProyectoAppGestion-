package clubCamaronesManagments.demo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrecioCuota {
    @Id
    private int id;
    @NonNull
    private double precio;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuota_id")
    @JsonBackReference
    private Cuota cuota;
}
