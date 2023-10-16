package com.example.demo;

import com.example.demo.models.Cancion;
import com.example.demo.models.Genero;
import com.example.demo.repostories.CancionRepository;
import com.example.demo.repostories.GeneroRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CancionTest {

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private CancionRepository cancionRepository;


    private Cancion cancion;

    @BeforeEach
    public void setUp() {
        cancion = new Cancion();
        cancion.setNombre("Canción de Prueba");
        cancion.setArtista("Autor de Prueba");
        cancion.setAlbum("Álbum de Prueba");
        cancion.setNumLikes(0L);
        cancion.setDuracion(new Time(0));
        cancion.setFechaLanzamiento(new Date());

        Genero genero = new Genero();
        genero.setNombre("Género de Prueba");
        genero.setDescripcion("Descripción de Prueba");

        genero = generoRepository.save(genero);

        cancion.setGenero(genero);
    }

    @Test
    @Order(1)
    void crearCancion() {
        Cancion cancionRecuperada = cancionRepository.save(cancion);

        Long id = cancionRecuperada.getId();
        Assertions.assertNotNull(id);
    }

    @Test
    @Order(2)
    void obtenerCanciones() {
        List<Cancion> canciones = (List<Cancion>) cancionRepository.findAll();
        Assertions.assertNotNull(canciones);
        Assertions.assertFalse(canciones.isEmpty());
    }

    @Test
    @Order(3)
    void obtenerCancion() {
        Cancion cancionRecuperada = cancionRepository.save(cancion);
        Long id = cancionRecuperada.getId();
        Assertions.assertNotNull(id);

        cancionRecuperada = cancionRepository.findById(id).orElse(null);
        Assertions.assertNotNull(cancionRecuperada);
    }

    @Test
    @Order(4)
    void actualizarCancion() {
        Cancion cancionRecuperada = cancionRepository.save(cancion);
        Long id = cancionRecuperada.getId();
        Assertions.assertNotNull(id);

        cancionRecuperada = cancionRepository.findById(id).orElse(null);
        Assertions.assertNotNull(cancionRecuperada);

        cancionRecuperada.setNombre("Canción de Prueba Actualizada");
        cancionRecuperada = cancionRepository.save(cancionRecuperada);

        Assertions.assertEquals("Canción de Prueba Actualizada", cancionRecuperada.getNombre());
    }

    @Test
    @Order(5)
    void eliminarCancion() {
        Cancion cancionRecuperada = cancionRepository.save(cancion);
        Long id = cancionRecuperada.getId();
        Assertions.assertNotNull(id);

        cancionRepository.deleteById(id);

        cancionRecuperada = cancionRepository.findById(id).orElse(null);
        Assertions.assertNull(cancionRecuperada);
    }
}
