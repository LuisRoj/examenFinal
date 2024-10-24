package pe.edu.cibertec.ExamenT3.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ExamenT3.model.Alumno;
import pe.edu.cibertec.ExamenT3.model.Usuario;
import pe.edu.cibertec.ExamenT3.repository.AlumnoRepository;
import pe.edu.cibertec.ExamenT3.repository.UsuarioRepository;
import pe.edu.cibertec.ExamenT3.service.AlumnoService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlumnoServiceImplement implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> respuesta = new HashMap<>();
        List<Alumno> alumnos = alumnoRepository.findAll();

        if (!alumnos.isEmpty()) {
            respuesta.put("mensaje", "Lista de alumnos");
            respuesta.put("alumnos", alumnos);
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "No existen registros de alumnos");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> findById(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Alumno alumno = alumnoRepository.findById(id).orElse(null);

        if (alumno != null) {
            respuesta.put("mensaje", "Alumno encontrado");
            respuesta.put("alumno", alumno);
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "Alumno no encontrado");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> save(Alumno alumno) {
        Map<String, Object> respuesta = new HashMap<>();
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findOneByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar fecha del usuario
        usuario.setFecha(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Establecer datos del alumno
        alumno.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
        alumno.setFecha(LocalDateTime.now());

        Alumno nuevoAlumno = alumnoRepository.save(alumno);
        respuesta.put("mensaje", "Alumno creado exitosamente");
        respuesta.put("alumno", nuevoAlumno);
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> update(Long id, Alumno alumnoDetails) {
        Map<String, Object> respuesta = new HashMap<>();
        Alumno alumno = alumnoRepository.findById(id).orElse(null);

        if (alumno != null) {
            String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuario = usuarioRepository.findOneByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Actualizar fecha del usuario
            usuario.setFecha(LocalDateTime.now());
            usuarioRepository.save(usuario);

            // Actualizar datos del alumno
            alumno.setNombre(alumnoDetails.getNombre());
            alumno.setApellido(alumnoDetails.getApellido());
            alumno.setDni(alumnoDetails.getDni());
            alumno.setCiclo(alumnoDetails.getCiclo());
            alumno.setEstado(alumnoDetails.getEstado());
            alumno.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
            alumno.setFecha(LocalDateTime.now());

            Alumno alumnoActualizado = alumnoRepository.save(alumno);
            respuesta.put("mensaje", "Alumno actualizado exitosamente");
            respuesta.put("alumno", alumnoActualizado);
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());

            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "Alumno no encontrado");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Map<String, Object>> delete(Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Alumno alumno = alumnoRepository.findById(id).orElse(null);

        if (alumno != null) {
            alumnoRepository.delete(alumno);
            respuesta.put("mensaje", "Alumno eliminado exitosamente");
            respuesta.put("status", HttpStatus.NO_CONTENT);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
        } else {
            respuesta.put("mensaje", "Alumno no encontrado");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}


