package pe.edu.cibertec.ExamenT3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ExamenT3.model.Alumno;
import pe.edu.cibertec.ExamenT3.service.AlumnoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> crearAlumno(@RequestBody Alumno alumno) {
        return alumnoService.save(alumno);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listarAlumnos() {
        return alumnoService.findAll();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> buscarAlumno(@PathVariable Long id) {
        return alumnoService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetails) {
        return alumnoService.update(id, alumnoDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> eliminarAlumno(@PathVariable Long id) {
        return alumnoService.delete(id);
    }
}


