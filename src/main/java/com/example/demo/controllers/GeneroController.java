package com.example.demo.controllers;

import com.example.demo.dto.GeneroDTO;
import com.example.demo.models.Genero;
import com.example.demo.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping(value = "/generos", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<GeneroDTO> getGeneros() {
        return generoService.getGeneros();
    }

    @GetMapping(value = "/genero/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO getGeneroById(@PathVariable Long id) {
        return generoService.getGeneroById(id);
    }

    @PostMapping(value = "/genero", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO createGenero(@RequestBody Genero genero) {
        return generoService.createGenero(genero);
    }

    @PutMapping(value = "/genero/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO updateGenero(@PathVariable Long id, @RequestBody Genero genero) {
        return generoService.updateGenero(id, genero);
    }

    @DeleteMapping(value = "/genero/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO deleteGenero(@PathVariable Long id) {
        return generoService.deleteGenero(id);
    }
}
