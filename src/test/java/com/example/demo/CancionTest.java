package com.example.demo;

import com.example.demo.models.Cancion;
import com.example.demo.models.Genero;
import com.example.demo.repostories.CancionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
public class CancionTest {

    @Autowired
    private CancionRepository cancionRepository;

    private Cancion cancion;

    @BeforeEach
    public void setUp() {
        cancion = new Cancion();
        cancion.setNombre("Canción de Prueba");
        cancion.setAutor("Autor de Prueba");
        cancion.setAlbum("Álbum de Prueba");
        cancion.setNumLikes(0L);
        cancion.setValoracion(5L);
        cancion.setDuracion(240L);
        cancion.setFechaLanzamiento(new Date());
        cancion.setActive(true);

        Genero genero = new Genero();
        genero.setNombre("Pop");

        cancion.setGenero(genero);
    }

    @AfterEach
    public void tearDown() {
        // Borra la canción de la base de datos al finalizar la prueba
        if (cancion != null && cancion.getId() != null) {
            cancionRepository.delete(cancion);
        }
    }

    @Test
    public void testGuardarYRecuperarCancion() {
        Cancion cancionGuardada = cancionRepository.save(cancion);
        Long id = cancionGuardada.getId();
        Assertions.assertNotNull(id);

        Cancion cancionRecuperada = cancionRepository.findById(id).orElse(null);
        Assertions.assertNotNull(cancionRecuperada);
        Assertions.assertEquals("Canción de Prueba", cancionRecuperada.getNombre());
        Assertions.assertEquals("Autor de Prueba", cancionRecuperada.getAutor());
        Assertions.assertEquals("Álbum de Prueba", cancionRecuperada.getAlbum());
        Assertions.assertEquals(0L, cancionRecuperada.getNumLikes());
        Assertions.assertEquals(5L, cancionRecuperada.getValoracion());
        Assertions.assertEquals(240L, cancionRecuperada.getDuracion());
        Assertions.assertTrue(cancionRecuperada.getActive());
        Assertions.assertEquals("Pop", cancionRecuperada.getGenero().getNombre());
    }

    @Test
    public void testActualizarCancion() {
        Cancion cancionGuardada = cancionRepository.save(cancion);
        Long id = cancionGuardada.getId();

        cancionGuardada.setNombre("Nueva Canción");
        cancionGuardada.setValoracion(4L);
        cancionGuardada.setDuracion(180L);

        Cancion cancionActualizada = cancionRepository.save(cancionGuardada);
        Assertions.assertEquals("Nueva Canción", cancionActualizada.getNombre());
        Assertions.assertEquals(4L, cancionActualizada.getValoracion());
        Assertions.assertEquals(180L, cancionActualizada.getDuracion());
    }

    @Test
    public void testEliminarCancion() {
        Cancion cancionGuardada = cancionRepository.save(cancion);
        Long id = cancionGuardada.getId();
        cancionRepository.deleteById(id);


    }

    @Test
    public void testBuscarCancionesPorAutor() {
        // Guardar dos canciones con el mismo autor
        Cancion cancion1 = new Cancion();
        cancion1.setNombre("Canción 1");
        cancion1.setAutor("Autor Común");

        Cancion cancion2 = new Cancion();
        cancion2.setNombre("Canción 2");
        cancion2.setAutor("Autor Común");

        cancionRepository.save(cancion1);
        cancionRepository.save(cancion2);

    }
}
