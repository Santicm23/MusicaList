package com.example.demo;

import com.example.demo.dto.CancionDTO;
import com.example.demo.models.Cancion;
import com.example.demo.services.CancionService;
import com.example.demo.services.GeneroService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.sql.Time;
import java.util.Date;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CancionTest {

    @Autowired
    private CancionService cancionService;

    @Autowired
    private GeneroService generoService;

    private Long cid;
    private Long gid;
    private Cancion cancion;

    @BeforeEach
    @Test
    public void testCreateCancion() {
        Assertions.assertDoesNotThrow(() -> {
            gid = generoService.getGeneros().get(0).getId();

            cancion = new Cancion("Test", "Test", "Test", "Test", gid, new Date(), new Time(0));
            CancionDTO cancionTemp = cancionService.createCancion(cancion);
            cid = cancionTemp.getId();

            Assertions.assertEquals(cancionTemp.getNombre(), cancion.getNombre());
            Assertions.assertEquals(cancionTemp.getArtista(), cancion.getArtista());
            Assertions.assertEquals(cancionTemp.getAlbum(), cancion.getAlbum());
            Assertions.assertEquals(cancionTemp.getImagen(), cancion.getImagen());
            Assertions.assertEquals(cancionTemp.getGenero().getId(), cancion.getGenero().getId());
        });
    }

    @Test
    @Order(1)
    public void testGetCanciones() {
        Assertions.assertFalse(cancionService.getCanciones().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetCancionById() {
        Assertions.assertDoesNotThrow(() -> {
            Long idTemp = cancionService.getCancionById(cid).getId();
            Assertions.assertEquals(idTemp, cid);
        });
    }

    @Test
    @Order(3)
    public void testGetCancionesByGenero() {
        Assertions.assertFalse(cancionService.getCancionesByGenero(gid).isEmpty());
    }

    @Test
    @Order(4)
    public void testBuscarCanciones() {
        Assertions.assertFalse(cancionService.buscarCanciones("Test").isEmpty());
    }

    @Test
    @Order(5)
    public void testUpdateCancion() {
        cancion.setNombre("Test2");
        Assertions.assertDoesNotThrow(() -> {
            CancionDTO cancionTemp = cancionService.updateCancion(cid, cancion);
            Assertions.assertEquals(cancionTemp.getNombre(), "Test2");
        });
    }

    @Test
    @Order(6)
    public void testDeleteCancion() {
        Assertions.assertDoesNotThrow(() -> cancionService.deleteCancion(cid));
    }
}
