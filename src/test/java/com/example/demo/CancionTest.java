package com.example.demo;

import com.example.demo.services.CancionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CancionTest {

    @Autowired
    private CancionService cancionService;

    @Test
    @Order(1)
    public void testGetCanciones() {
        Assertions.assertFalse(cancionService.getCanciones().isEmpty());
    }

    @Test
    @Order(2)
    public void testGetCancionesByGenero() {
        Assertions.assertFalse(cancionService.getCancionesByGenero(1L).isEmpty());
    }

    @Test
    public void testGetCancionById() {
        Assertions.assertDoesNotThrow(() -> cancionService.getCancionById(1L));
    }
}
