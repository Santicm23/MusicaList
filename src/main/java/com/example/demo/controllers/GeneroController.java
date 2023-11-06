package com.example.demo.controllers;

import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.GeneroDTO;
import com.example.demo.models.Genero;
import com.example.demo.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping(value = "/generos", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public List<GeneroDTO> getGeneros() {
        return generoService.getGeneros();
    }

    @GetMapping(value = "/genero/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO getGeneroById(@PathVariable Long id) throws StandardRequestException {
        return generoService.getGeneroById(id);
    }

    @PostMapping(value = "/genero", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO createGenero(@RequestBody Genero genero) throws StandardRequestException {
        return generoService.createGenero(genero);
    }

    @PutMapping(value = "/genero/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO updateGenero(@PathVariable Long id, @RequestBody Genero genero) throws StandardRequestException {
        return generoService.updateGenero(id, genero);
    }

    @DeleteMapping(value = "/genero/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public GeneroDTO deleteGenero(@PathVariable Long id) throws StandardRequestException {
        return generoService.deleteGenero(id);
    }
}
