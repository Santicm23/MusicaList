package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repostories.UsuarioRepository;

@SpringBootTest
@Transactional
public class UsuarioTest {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Test
    void obtenerUsuarios() {

    }

    @Test
    void obtenerUsuario() {

    }

    @Test
    void crearUsuario() {

    }

    @Test
    void actualizarUsuario() {

    }

    @Test
    void eliminarUsuario() {

    }
}


