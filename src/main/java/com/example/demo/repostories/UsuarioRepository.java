package com.example.demo.repostories;

import com.example.demo.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    List<Usuario> findByCorreo(String correo);
}
