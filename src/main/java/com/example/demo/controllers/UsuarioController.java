package com.example.demo.controllers;

import com.example.demo.models.Usuario;
import com.example.demo.repostories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/usuarios", produces = "application/json")
    public Iterable<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping(value = "/usuario/{uid}", produces = "application/json")
    public Usuario getUsuarioById(@PathVariable Long uid) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uid);

        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    @PostMapping(value = "/usuario", produces = "application/json")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping(value = "/usuario/{uid}", produces = "application/json")
    public Usuario updateUsuario(@PathVariable Long uid, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uid);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioTemp = usuarioOptional.get();
            usuarioTemp.setNombre(usuario.getNombre());
            usuarioTemp.setCorreo(usuario.getCorreo());
            usuarioTemp.setContrasena(usuario.getContrasena());
            usuarioTemp.setRol(usuario.getRol());

            return usuarioRepository.save(usuarioTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    @DeleteMapping(value = "/usuario/{uid}", produces = "application/json")
    public Usuario deleteUsuario(@PathVariable Long uid) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uid);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioTemp = usuarioOptional.get();
            usuarioTemp.setActivo(false);

            return usuarioRepository.save(usuarioTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }
}
