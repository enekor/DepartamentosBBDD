package POJO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@Data
public class Departamento {
    private int id;
    private List<Programador> progamadores;
    private Programador lider;
    private String nombre;
    private double presupuesto;
}