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

    private Long id;
    private Genero genero;

    @BeforeEach
    @Test
    public void testCreateGenero() {
        genero = new Genero("Test", "Test", "Test");
        Assertions.assertDoesNotThrow(() -> {
            GeneroDTO generoTemp = generoService.createGenero(genero);
            id = genero.getId();
            System.out.println(id);
            Assertions.assertEquals(generoTemp.getNombre(), genero.getNombre());
            Assertions.assertEquals(generoTemp.getDescripcion(), genero.getDescripcion());
            Assertions.assertEquals(generoTemp.getImagen(), genero.getImagen());
        });
    }

    @Test
    @Order(1)
    public void testGetGeneros() {
        System.out.println(id);
        Assertions.assertFalse(generoService.getGeneros().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetGeneroById() {
        System.out.println(id);
        Assertions.assertDoesNotThrow(() -> {
            Long idTemp = generoService.getGeneroById(id).getId();
            Assertions.assertEquals(idTemp, id);
        });
    }

    @Test
    @Order(3)
    public void testUpdateGenero() {
        System.out.println(id);
        genero.setNombre("Test2");
        Assertions.assertDoesNotThrow(() -> {
            GeneroDTO generoTemp = generoService.updateGenero(id, genero);
            Assertions.assertEquals(generoTemp.getNombre(), "Test2");
        });
    }

    @Test
    @Order(4)
    public void testDeleteGenero() {
        System.out.println(id);
        Assertions.assertDoesNotThrow(() -> generoService.deleteGenero(id));
    }
}




