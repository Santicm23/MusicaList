package com.example.demo;

import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GeneroTest {

    @Autowired
    protected GeneroRepository GeneroRepository;

    private Long id;
    private Genero genero;

    @BeforeEach
    public void setUp() {
        genero = new Genero();
        genero.setNombre("Género de Prueba");
        genero.setDescripcion("Descripción de Prueba");

        genero = GeneroRepository.save(genero);

        id = genero.getId();
    }

    @Test
    @Order(1)
    void crearGenero() {
        Genero generoRecuperado = GeneroRepository.save(genero);

        Long id = generoRecuperado.getId();
        assertNotNull(id);
    }

    @Test
    @Order(2)
    void obtenerGeneros() {
        List<Genero> generos = (List<Genero>) GeneroRepository.findAll();

        assertFalse(generos.isEmpty());
    }

    @Test
    @Order(3)
    void obtenerGenero() {
        Optional<Genero> generoRecuperado = GeneroRepository.findById(id);

        assertTrue(generoRecuperado.isPresent());
    }

    @Test
    @Order(4)
    void actualizarGenero() {
        genero.setNombre("Género de Prueba Actualizado");
        genero.setDescripcion("Descripción de Prueba Actualizada");

        Genero generoRecuperado = GeneroRepository.save(genero);

        assertEquals(genero.getNombre(), generoRecuperado.getNombre());
        assertEquals(genero.getDescripcion(), generoRecuperado.getDescripcion());
    }

    @Test
    @Order(5)
    void eliminarGenero() {
        GeneroRepository.deleteById(id);

        Optional<Genero> generoRecuperado = GeneroRepository.findById(id);

        assertFalse(generoRecuperado.isPresent());
    }

}




