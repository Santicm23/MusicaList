package com.example.demo.controllers;

import com.example.demo.model.Cancion;
import com.example.demo.model.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;

    @GetMapping(value = "/canciones", produces = "application/json")
    public Iterable<Cancion> getCanciones() {
        return cancionRepository.findAll();
    }

    @GetMapping(value = "/cancion/{cid}", produces = "application/json")
    public Cancion getCancionById(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            return cancionOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cancion no encontrada");
        }
    }

    @PostMapping(value = "/cancion", produces = "application/json")
    public Cancion createCancion(@RequestBody Cancion cancion) {
        return cancionRepository.save(cancion);
    }

    @PutMapping(value = "/cancion/{cid}", produces = "application/json")
    public Cancion updateCancion(@PathVariable Long cid, @RequestBody Cancion cancion) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            Cancion cancionTemp = cancionOptional.get();
            cancionTemp.setNombre(cancion.getNombre());
            cancionTemp.setDuracion(cancion.getDuracion());
            cancionTemp.setGenero(cancion.getGenero());
            cancionTemp.setAutor(cancion.getAutor());
            cancionTemp.setAlbum(cancion.getAlbum());
            cancionTemp.setNumLikes(cancion.getNumLikes());
            cancionTemp.setValoracion(cancion.getValoracion());
            cancionTemp.setFechaLanzamiento(cancion.getFechaLanzamiento());
            cancionTemp.setActive(cancion.getActive());

            return cancionRepository.save(cancionTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cancion no encontrada");
        }
    }

    @DeleteMapping(value = "/cancion/{cid}", produces = "application/json")
    public Cancion deleteCancion(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            Cancion cancionTemp = cancionOptional.get();
            cancionTemp.setActive(false);
            cancionRepository.save(cancionTemp);

            return cancionTemp;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cancion no encontrada");
        }
    }
}
