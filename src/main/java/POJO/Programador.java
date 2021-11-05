package POJO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@Data
public class Programador {
    private int id;
    private String nombre;
    private List<String> lenguajes;
    private int experiencia;
    private double salario;
    private Departamento jefe = null;
    private Departamento trabajo;
}
