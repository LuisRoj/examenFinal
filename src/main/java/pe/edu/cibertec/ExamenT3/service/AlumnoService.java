package pe.edu.cibertec.ExamenT3.service;

import pe.edu.cibertec.ExamenT3.model.Alumno;

import java.util.List;

public interface AlumnoService {

    // Método para obtener la lista de todos los alumnos
    List<Alumno> findAll();

    // Método para obtener un alumno por su ID
    Alumno findById(Long id);

    // Método para crear un nuevo alumno
    Alumno save(Alumno alumno);

    // Método para actualizar un alumno existente
    Alumno update(Long id, Alumno alumnoDetails);

    // Método para eliminar un alumno por su ID
    void delete(Long id);
}

