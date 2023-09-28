package com.example.demo;

import com.example.demo.models.Cancion;
import com.example.demo.models.TipoUsuario;
import com.example.demo.models.Usuario;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repostories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UsuarioTest {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Usuario de Prueba");
        usuario.setCorreo("prueba@example.com");
        usuario.setContrasena("password");
        usuario.setActivo(true);

        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setNombre("Usuario Normal");

        usuario.setRol(tipoUsuario);

        List<Cancion> canciones = new ArrayList<>();


        usuario.setLikesDeCanciones(canciones);
    }

    @Test
    public void testGuardarYRecuperarUsuario() {
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        Long id = usuarioGuardado.getId();
        assertNotNull(id);

        Usuario usuarioRecuperado = usuarioRepository.findById(id).orElse(null);
        assertNotNull(usuarioRecuperado);
        assertEquals("Usuario de Prueba", usuarioRecuperado.getNombre());
        assertEquals("prueba@example.com", usuarioRecuperado.getCorreo());
        assertEquals("password", usuarioRecuperado.getContrasena());
        assertTrue(usuarioRecuperado.getActivo());
        assertEquals("Usuario Normal", usuarioRecuperado.getRol().getNombre());

    }


    @Test
    void obtenerUsuarios() {

    }

    @Test
    void obtenerUsuario() {

    }

    @Test
    void crearUsuario() {

    }

    @Test
    void actualizarUsuario() {

    }

    @Test
    void eliminarUsuario() {

    }
}


