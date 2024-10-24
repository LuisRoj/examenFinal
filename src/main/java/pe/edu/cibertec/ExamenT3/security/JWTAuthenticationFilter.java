package pe.edu.cibertec.ExamenT3.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.edu.cibertec.ExamenT3.model.Auth;
import pe.edu.cibertec.ExamenT3.model.Usuario;
import pe.edu.cibertec.ExamenT3.repository.UsuarioRepository;
import pe.edu.cibertec.ExamenT3.serviceImplement.UserDetailImplement;
import pe.edu.cibertec.ExamenT3.util.Token;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public JWTAuthenticationFilter(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        Auth authCredenciales = new Auth();

        try {
            authCredenciales = new ObjectMapper().readValue(request.getReader(), Auth.class);
        } catch (Exception e) {
            System.out.println("Error al leer las credenciales de autenticación: " + e.getMessage());
        }

        UsernamePasswordAuthenticationToken userPAT = new UsernamePasswordAuthenticationToken(
                authCredenciales.getEmail(),
                authCredenciales.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(userPAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailImplement userDetails = (UserDetailImplement) authResult.getPrincipal();
        String email = userDetails.getUsername();

        System.out.println("Autenticación: " + email);

        Usuario usuario = usuarioRepository.findOneByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("Usuario no encontrado con email: " + email);
                    return new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
                });

        //actualizar fecha
        if (usuario != null) {
            usuario.setFecha(LocalDateTime.now());
            usuarioRepository.save(usuario);
            System.out.println("Fecha actualizada para el usuario con email: " + email);
        } else {
            System.out.println("No se pudo actualizar la fecha porque el usuario no existe.");
        }

        // Generación del token JWT
        String token = Token.crearToken(userDetails.getNombre(), email);
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}

