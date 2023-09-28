package com.example.demo.controllers;

import com.example.demo.models.TipoUsuario;
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
        try {
            if (usuario.getRol() == null) {
                usuario.setRol(new TipoUsuario(2L));
            }
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se pudo crear el usuario");
        }
    }

    @PutMapping(value = "/usuario/{uid}", produces = "application/json")
    public Usuario updateUsuario(@PathVariable Long uid, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uid);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioTemp = usuarioOptional.get();

            if (usuario.getNombre() != null) {
                usuarioTemp.setNombre(usuario.getNombre());
            }
            if (usuario.getCorreo() != null) {
                usuarioTemp.setCorreo(usuario.getCorreo());
            }
            if (usuario.getContrasena() != null) {
                usuarioTemp.setContrasena(usuario.getContrasena());
            }
            if (usuario.getRol() != null) {
                usuarioTemp.setRol(usuario.getRol());
            }

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
