package com.example.demo.controllers;

import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class GeneroController {

    @Autowired
    private GeneroRepository generoRepository;

    @GetMapping(value = "/generos", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Genero> getGeneros() {
        return generoRepository.findByActivoTrue();
    }

    @GetMapping(value = "/genero/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Genero getGeneroById(@PathVariable Long id) {
        Optional<Genero> generoOptional = generoRepository.findById(id);

        if (generoOptional.isPresent()) {
            return generoOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Genero no encontrado");
        }
    }

    @PostMapping(value = "/genero", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Genero createGenero(@RequestBody Genero genero) {
        try {
            return generoRepository.save(genero);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error al crear el genero");
        }
    }

    @PutMapping(value = "/genero/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Genero updateGenero(@PathVariable Long id, @RequestBody Genero genero) {
        Optional<Genero> generoOptional = generoRepository.findById(id);

        if (generoOptional.isPresent()) {
            Genero generoTemp = generoOptional.get();

            if (genero.getNombre() != null) {
                generoTemp.setNombre(genero.getNombre());
            }
            if (genero.getDescripcion() != null) {
                generoTemp.setDescripcion(genero.getDescripcion());
            }
            if (genero.getImagen() != null) {
                generoTemp.setImagen(genero.getImagen());
            }

            return generoRepository.save(generoTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Genero no encontrado");
        }
    }

    @DeleteMapping(value = "/genero/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteGenero(@PathVariable Long id) {
        Optional<Genero> generoOptional = generoRepository.findById(id);

        if (generoOptional.isPresent()) {
            Genero generoTemp = generoOptional.get();
            generoTemp.setActivo(false);
            generoRepository.save(generoTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Genero no encontrado");
        }
    }
}
