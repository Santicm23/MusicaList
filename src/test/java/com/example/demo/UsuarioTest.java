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

    Long id;
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
    void crearUsuario() {
        Usuario usuarioRecuperado = usuarioRepository.save(usuario);
        id = usuarioRecuperado.getId();
        assertNotNull(id);
    }

    @Test
    void obtenerUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void obtenerUsuario() {

        Usuario usuarioRecuperado = usuarioRepository.findById(id).orElse(null);
        assertNotNull(usuarioRecuperado);
        assertEquals(usuario.getNombre(), usuarioRecuperado.getNombre());
        assertEquals(usuario.getCorreo(), usuarioRecuperado.getCorreo());
        assertEquals(usuario.getContrasena(), usuarioRecuperado.getContrasena());
        assertTrue(usuarioRecuperado.getActivo());
        assertEquals(usuario.getRol().getNombre(), usuarioRecuperado.getRol().getNombre());
    }


    @Test
    void actualizarUsuario() {
        Usuario usuarioRecuperado = usuarioRepository.findById(id).orElse(null);
        assertNotNull(usuarioRecuperado);
        usuarioRecuperado.setNombre("Usuario de Prueba Actualizado");

        Usuario usuarioActualizado = usuarioRepository.save(usuarioRecuperado);

        assertEquals("Usuario de Prueba Actualizado", usuarioActualizado.getNombre());
    }

    @Test
    void eliminarUsuario() {
        usuarioRepository.deleteById(id);

        Usuario usuarioRecuperado = usuarioRepository.findById(id).orElse(null);
        assertNull(usuarioRecuperado);
    }
}


