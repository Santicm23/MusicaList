package com.example.demo;

import com.example.demo.models.Cancion;
import com.example.demo.models.TipoUsuario;
import com.example.demo.models.Usuario;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repostories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTest {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    private Long id;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNombre("Usuario de Prueba");
        usuario.setCorreo("prueba@example.com");
        usuario.setContrasena("password");
        usuario.setRol(new TipoUsuario(2L));

        List<Cancion> canciones = new ArrayList<>();

        usuario.setLikesDeCanciones(canciones);

        Usuario usuarioRecuperado = usuarioRepository.save(usuario);

        id = usuarioRecuperado.getId();
    }

    @Test
    @Order(1)
    void crearUsuario() {
        Usuario usuarioRecuperado = usuarioRepository.save(usuario);

        Long id = usuarioRecuperado.getId();
        assertNotNull(id);
    }

    @Test
    @Order(2)
    void obtenerUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }

    @Test
    @Order(3)
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
    @Order(4)
    void actualizarUsuario() {
        Usuario usuarioRecuperado = usuarioRepository.findById(id).orElse(null);
        assertNotNull(usuarioRecuperado);
        String nombrePrueba = "Usuario de Prueba Actualizado";
        usuarioRecuperado.setNombre(nombrePrueba);

        Usuario usuarioActualizado = usuarioRepository.save(usuarioRecuperado);

        assertEquals(nombrePrueba, usuarioActualizado.getNombre());
    }

    @Test
    @Order(5)
    void eliminarUsuario() {
        usuarioRepository.deleteById(id);

        Usuario usuarioRecuperado = usuarioRepository.findById(id).orElse(null);
        assertNull(usuarioRecuperado);
    }
}


