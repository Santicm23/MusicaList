package com.example.demo;

import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class GeneroTest {

    @Autowired
    protected GeneroRepository GeneroRepository;

    private Long id;
    private Genero genero;


    @BeforeEach
    public void setUp() {
        genero = new Genero();
        genero.setNombre("Rock");
        genero.setDescripcion("Rock Description");

    }
    @Test
    public void crearGenero() {
        Genero nuevoGenero = GeneroRepository.save(genero);
        id = nuevoGenero.getId();

        assertNotNull(id);
    }
    @Test
    public void obtenerGeneros() {
        List<Genero> generos = (List<Genero>) GeneroRepository.findAll();
        assertFalse(generos.isEmpty());
    }
    @Test
    public void obtenerGeneroPorId() {
        Genero generoRecuperado = GeneroRepository.findById(id).orElse(null);

        assertNotNull(generoRecuperado);

        assertEquals(id, genero.getId());
        assertEquals(genero.getNombre(), generoRecuperado.getNombre());
        assertEquals(genero.getDescripcion(), generoRecuperado.getDescripcion());
    }
    @Test
    public void actualizarGeneroPorId() {
        Genero generoRecuperado = GeneroRepository.findById(id).orElse(null);

        assertNotNull(generoRecuperado);

        generoRecuperado.setNombre("Pop");
        generoRecuperado.setDescripcion("Pop Description");

        Genero updatedGenero = GeneroRepository.save(generoRecuperado);

        assertEquals(id, updatedGenero.getId());
        assertEquals(genero.getNombre(), updatedGenero.getNombre());
        assertEquals(genero.getDescripcion(), updatedGenero.getDescripcion());
    }
    @Test
    public void eliminarGeneroPorId() {

        GeneroRepository.deleteById(id);

        Genero generoRecuperado = GeneroRepository.findById(id).orElse(null);
        assertNull(generoRecuperado);
    }
}




