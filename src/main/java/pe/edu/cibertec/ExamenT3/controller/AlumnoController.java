package pe.edu.cibertec.ExamenT3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.ExamenT3.model.Alumno;
import pe.edu.cibertec.ExamenT3.service.AlumnoService;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/create")
    public ResponseEntity<Alumno> crearAlumno(@RequestBody Alumno alumno) {
        Alumno nuevoAlumno = alumnoService.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        return ResponseEntity.ok(alumnoService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Alumno> buscarAlumno(@PathVariable Long id) {
        Alumno alumno = alumnoService.findById(id);
        return ResponseEntity.ok(alumno);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumnoDetails) {
        Alumno updatedAlumno = alumnoService.update(id, alumnoDetails);
        return ResponseEntity.ok(updatedAlumno);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        alumnoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

