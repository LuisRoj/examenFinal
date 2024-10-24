package pe.edu.cibertec.ExamenT3.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime fecha;
}