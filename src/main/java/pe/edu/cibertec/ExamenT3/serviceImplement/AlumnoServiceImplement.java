package pe.edu.cibertec.ExamenT3.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ExamenT3.model.Alumno;
import pe.edu.cibertec.ExamenT3.model.Usuario;
import pe.edu.cibertec.ExamenT3.repository.AlumnoRepository;
import pe.edu.cibertec.ExamenT3.repository.UsuarioRepository;
import pe.edu.cibertec.ExamenT3.service.AlumnoService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class AlumnoServiceImplement implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Alumno> findAll() {
        return alumnoRepository.findAll();
    }

    @Override
    public Alumno findById(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
    }

    @Transactional
    @Override
    public Alumno save(Alumno alumno) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findOneByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar fecha del usuario
        usuario.setFecha(new Date());
        usuarioRepository.save(usuario);

        // Establecer datos del alumno
        alumno.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
        alumno.setFecha(new Date());

        return alumnoRepository.save(alumno);
    }

    @Transactional
    @Override
    public Alumno update(Long id, Alumno alumnoDetails) {
        Alumno alumno = findById(id);
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findOneByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar fecha del usuario
        usuario.setFecha(new Date());
        usuarioRepository.save(usuario);

        // Actualizar datos del alumno
        alumno.setNombre(alumnoDetails.getNombre());
        alumno.setApellido(alumnoDetails.getApellido());
        alumno.setDni(alumnoDetails.getDni());
        alumno.setCiclo(alumnoDetails.getCiclo());
        alumno.setEstado(alumnoDetails.getEstado());
        alumno.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
        alumno.setFecha(new Date());

        return alumnoRepository.save(alumno);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Alumno alumno = findById(id);
        alumnoRepository.delete(alumno);
    }
}

