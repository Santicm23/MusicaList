package com.example.demo.controllers;

import com.example.demo.model.TipoUsuario;
import com.example.demo.model.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @GetMapping(value = "/tiposUsuario", produces = "application/json")
    public Iterable<TipoUsuario> getTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }
}
