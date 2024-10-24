package pe.edu.cibertec.ExamenT3.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "ALUMNO")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 8, unique = true)
    private String dni;

    private Integer ciclo;

    @Column(length = 1)
    private String estado;

    @Column(name = "nombre_usuario", length = 100)
    private String nombreUsuario;

    @Column(nullable = false)
    private Date fecha;
}
