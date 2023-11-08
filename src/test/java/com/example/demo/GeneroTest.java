package com.example.demo;

import com.example.demo.dto.GeneroDTO;
import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.models.Genero;
import com.example.demo.services.GeneroService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
//@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeneroTest {

    @Autowired
    private GeneroService generoService;

    private Long gid;
    private Genero genero;

    /*@BeforeEach
    @Test
    public void testCreateGenero() throws StandardRequestException {
        genero = new Genero("Test", "Test", "Test");
        GeneroDTO generoTemp = generoService.createGenero(genero);
        gid = genero.getId();

        Assertions.assertEquals(generoTemp.getNombre(), genero.getNombre());
        Assertions.assertEquals(generoTemp.getDescripcion(), genero.getDescripcion());
        Assertions.assertEquals(generoTemp.getImagen(), genero.getImagen());
    }

    @Test
    @Order(1)
    public void testGetGeneros() {
        Assertions.assertFalse(generoService.getGeneros().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetGeneroById() throws StandardRequestException {
        Long idTemp = generoService.getGeneroById(gid).getId();
        Assertions.assertEquals(idTemp, gid);
    }

    @Test
    @Order(3)
    public void testUpdateGenero() throws StandardRequestException {
        genero.setNombre("Test2");
        GeneroDTO generoTemp = generoService.updateGenero(gid, genero);
        Assertions.assertEquals(generoTemp.getNombre(), "Test2");
    }

    @Test
    @Order(4)
    public void testDeleteGenero() throws StandardRequestException {
        generoService.deleteGenero(gid);
        Assertions.assertThrows(StandardRequestException.class, () -> generoService.getGeneroById(gid));
    }*/
}




