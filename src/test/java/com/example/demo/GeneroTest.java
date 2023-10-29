package com.example.demo;

import com.example.demo.dto.GeneroDTO;
import com.example.demo.models.Genero;
import com.example.demo.services.GeneroService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeneroTest {

    @Autowired
    private GeneroService generoService;

    private Long gid;
    private Genero genero;

    @BeforeEach
    @Test
    public void testCreateGenero() {
        genero = new Genero("Test", "Test", "Test");
        Assertions.assertDoesNotThrow(() -> {
            GeneroDTO generoTemp = generoService.createGenero(genero);
            gid = genero.getId();

            Assertions.assertEquals(generoTemp.getNombre(), genero.getNombre());
            Assertions.assertEquals(generoTemp.getDescripcion(), genero.getDescripcion());
            Assertions.assertEquals(generoTemp.getImagen(), genero.getImagen());
        });
    }

    @Test
    @Order(1)
    public void testGetGeneros() {
        Assertions.assertFalse(generoService.getGeneros().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetGeneroById() {
        Assertions.assertDoesNotThrow(() -> {
            Long idTemp = generoService.getGeneroById(gid).getId();
            Assertions.assertEquals(idTemp, gid);
        });
    }

    @Test
    @Order(3)
    public void testUpdateGenero() {
        genero.setNombre("Test2");
        Assertions.assertDoesNotThrow(() -> {
            GeneroDTO generoTemp = generoService.updateGenero(gid, genero);
            Assertions.assertEquals(generoTemp.getNombre(), "Test2");
        });
    }

    @Test
    @Order(4)
    public void testDeleteGenero() {
        Assertions.assertDoesNotThrow(() -> generoService.deleteGenero(gid));
    }
}




