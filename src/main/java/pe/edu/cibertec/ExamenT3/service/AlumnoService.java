package pe.edu.cibertec.ExamenT3.service;

import org.springframework.http.ResponseEntity;
import pe.edu.cibertec.ExamenT3.model.Alumno;

import java.util.List;
import java.util.Map;

public interface AlumnoService {
    ResponseEntity<Map<String, Object>> findAll();

    ResponseEntity<Map<String, Object>> findById(Long id);

    ResponseEntity<Map<String, Object>> save(Alumno alumno);

    ResponseEntity<Map<String, Object>> update(Long id, Alumno alumnoDetails);

    ResponseEntity<Map<String, Object>> delete(Long id);
}


