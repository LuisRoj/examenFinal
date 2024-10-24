package pe.edu.cibertec.ExamenT3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.ExamenT3.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    boolean existsByDni(String dni);
}
