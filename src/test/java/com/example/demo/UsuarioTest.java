package com.example.demo;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.exceptions.StandardRequestException;
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

    @BeforeEach
    @Test
    public void testCreateUsuario() throws StandardRequestException {

        cid = cancionService.getCanciones().get(0).getId();

        Usuario usuario = new Usuario("Test", "Test", "Test");
        UsuarioDTO  usuarioTemp = usuarioService.createUsuario(usuario);
        uid = usuarioTemp.getUid();

        Assertions.assertEquals(usuarioTemp.getNombre(), usuario.getNombre());
        Assertions.assertEquals(usuarioTemp.getCorreo(), usuario.getCorreo());
        Assertions.assertTrue(Hashing.checkPassword(
                usuario.getContrasena(), Hashing.getHash("Test")));
    }

    @Test
    @Order(1)
    public void testGetUsuarios() {
        Assertions.assertFalse(usuarioService.getUsuarios().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetUsuarioById() throws StandardRequestException {
        Long idTemp = usuarioService.getUsuarioById(uid).getUid();
        Assertions.assertEquals(idTemp, uid);
    }

    @Test
    @Order(3)
    public void testGetCancionesByUsuario() throws StandardRequestException {
        usuarioService.addCancionToUsuario(uid, cid);
        Assertions.assertFalse(usuarioService.getCancionesByUsuario(uid).isEmpty());
    }

    @Test
    @Order(4)
    public void testAddCancionToUsuario() throws StandardRequestException {
        Assertions.assertFalse(usuarioService.addCancionToUsuario(uid, cid).getCanciones().isEmpty());
    }

    @Test
    @Order(5)
    public void testDeleteCancionFromUsuario() throws StandardRequestException {
        Assertions.assertTrue(usuarioService.deleteCancionFromUsuario(uid, cid).getCanciones().isEmpty());
    }
}


