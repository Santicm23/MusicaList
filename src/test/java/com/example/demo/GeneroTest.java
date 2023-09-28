package com.example.demo;

import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
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
public class GeneroTest {

    @Autowired
    protected GeneroRepository GeneroRepository;

    private Genero genero;
    private Genero nuevoGenero;


    @BeforeEach
    public void setUp() {

        Genero genero1 = new Genero();
        genero1.setId(1L);
        genero1.setNombre("Rock");
        genero1.setDescripcion("Rock Description");

        Genero genero2 = new Genero();
        genero2.setId(2L);
        genero2.setNombre("Pop");
        genero2.setDescripcion("Pop Description");

        List<Genero> generos = Arrays.asList(genero1, genero2);

        when(GeneroRepository.findAll()).thenReturn(generos);

    }
    @Test
    public void testInsertGenero() {
        Genero savedGenero = GeneroRepository.save(nuevoGenero);

        assertNotNull(savedGenero);
        assertEquals(1L, savedGenero.getId());
        assertEquals("Jazz", savedGenero.getNombre());
        assertEquals("Jazz Description", savedGenero.getDescripcion());

        verify(GeneroRepository, times(1)).save(nuevoGenero);
    }
    @Test
    public void testGetAllGeneros() {
        List<Genero> generos = (List<Genero>) GeneroRepository.findAll();

        assertNotNull(generos);
        assertEquals(2, generos.size());

        Genero genero1 = generos.get(0);
        assertEquals(1L, genero1.getId());
        assertEquals("Rock", genero1.getNombre());
        assertEquals("Rock Description", genero1.getDescripcion());

        Genero genero2 = generos.get(1);
        assertEquals(2L, genero2.getId());
        assertEquals("Pop", genero2.getNombre());
        assertEquals("Pop Description", genero2.getDescripcion());

        verify(GeneroRepository, times(1)).findAll();
    }
    @Test
    public void testGetGeneroById() {
        Optional<Genero> optionalGenero = GeneroRepository.findById(1L);

        assertTrue(optionalGenero.isPresent());

        Genero genero = optionalGenero.get();
        assertEquals(1L, genero.getId());
        assertEquals("Rock", genero.getNombre());
        assertEquals("Rock Description", genero.getDescripcion());

        verify(GeneroRepository, times(1)).findById(1L);
    }
    @Test
    public void testUpdateGeneroById() {
        Optional<Genero> optionalGenero = GeneroRepository.findById(1L);
        assertTrue(optionalGenero.isPresent());

        Genero generoToUpdate = optionalGenero.get();
        generoToUpdate.setNombre("Pop");
        generoToUpdate.setDescripcion("Pop Description");

        when(GeneroRepository.save(generoToUpdate)).thenReturn(generoToUpdate);
        Genero updatedGenero = GeneroRepository.save(generoToUpdate);

        assertEquals(1L, updatedGenero.getId());
        assertEquals("Pop", updatedGenero.getNombre());
        assertEquals("Pop Description", updatedGenero.getDescripcion());

        verify(GeneroRepository, times(1)).findById(1L);
        verify(GeneroRepository, times(1)).save(generoToUpdate);
    }
    @Test
    public void testDeleteGeneroById() {
        Optional<Genero> optionalGenero = GeneroRepository.findById(1L);
        assertTrue(optionalGenero.isPresent());

        GeneroRepository.deleteById(1L);
        when(GeneroRepository.findById(1L)).thenReturn(Optional.empty());
        optionalGenero = GeneroRepository.findById(1L);

        assertFalse(optionalGenero.isPresent());

        verify(GeneroRepository, times(1)).findById(1L);
        verify(GeneroRepository, times(1)).deleteById(1L);
    }
}




