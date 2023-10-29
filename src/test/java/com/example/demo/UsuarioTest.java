package com.example.demo;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.helpers.Hashing;
import com.example.demo.models.Usuario;
import com.example.demo.services.CancionService;
import com.example.demo.services.UsuarioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CancionService cancionService;

    private Long uid;
    private Long cid;
    private Usuario usuario;

    @BeforeEach
    @Test
    public void testCreateUsuario() {
        Assertions.assertDoesNotThrow(() -> {
            cid = cancionService.getCanciones().get(0).getId();

            usuario = new Usuario("Test", "Test", "Test");
            UsuarioDTO  usuarioTemp = usuarioService.createUsuario(usuario);
            uid = usuarioTemp.getUid();

            Assertions.assertEquals(usuarioTemp.getNombre(), usuario.getNombre());
            Assertions.assertEquals(usuarioTemp.getCorreo(), usuario.getCorreo());
            Assertions.assertTrue(Hashing.checkPassword(usuario.getContrasena(), "Test"));
        });
    }

    @Test
    @Order(1)
    public void testGetUsuarios() {
        Assertions.assertFalse(usuarioService.getUsuarios().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetUsuarioById() {
        Assertions.assertDoesNotThrow(() -> {
            Long idTemp = usuarioService.getUsuarioById(uid).getUid();
            Assertions.assertEquals(idTemp, uid);
        });
    }

    @Test
    @Order(3)
    public void testGetCancionesByUsuario() {
        Assertions.assertDoesNotThrow(() -> Assertions.assertFalse(usuarioService.getCancionesByUsuario(uid).isEmpty()));
    }

    @Test
    @Order(4)
    public void testAddCancionToUsuario() {
        Assertions.assertDoesNotThrow(() -> Assertions.assertFalse(usuarioService.addCancionToUsuario(uid, cid).getCanciones().isEmpty()));
    }

    @Test
    @Order(5)
    public void testDeleteCancionFromUsuario() {
        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(usuarioService.deleteCancionFromUsuario(uid, cid).getCanciones().isEmpty()));
    }
}


