package com.example.demo.controllers;

import com.example.demo.models.Cancion;
import com.example.demo.repostories.CancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;

    @GetMapping(value = "/canciones", produces = "application/json")
    public Iterable<Cancion> getCanciones() {
        // filtrar canciones activas
        List<Cancion> canciones = new ArrayList<>();

        cancionRepository.findAll().forEach(cancion -> {
            if (cancion.getActive()) {
                canciones.add(cancion);
            }
        });

        return canciones;
    }

    @GetMapping(value = "/cancion/{cid}", produces = "application/json")
    public Cancion getCancionById(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent() && cancionOptional.get().getActive()) {
            return cancionOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cancion no encontrada");
        }
    }

    @PostMapping(value = "/cancion", produces = "application/json")
    public Cancion createCancion(@RequestBody Cancion cancion) {
        try {
            return cancionRepository.save(cancion);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error al crear la cancion");
        }
    }

    @PutMapping(value = "/cancion/{cid}", produces = "application/json")
    public Cancion updateCancion(@PathVariable Long cid, @RequestBody Cancion cancion) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            Cancion cancionTemp = cancionOptional.get();

            if (cancion.getNombre() != null) {
                cancionTemp.setNombre(cancion.getNombre());
            }
            if (cancion.getDuracion() != null) {
                cancionTemp.setDuracion(cancion.getDuracion());
            }
            if (cancion.getGenero() != null) {
                cancionTemp.setGenero(cancion.getGenero());
            }
            if (cancion.getAutor() != null) {
                cancionTemp.setAutor(cancion.getAutor());
            }
            if (cancion.getAlbum() != null) {
                cancionTemp.setAlbum(cancion.getAlbum());
            }
            if (cancion.getNumLikes() != null) {
                cancionTemp.setNumLikes(cancion.getNumLikes());
            }
            if (cancion.getValoracion() != null) {
                cancionTemp.setValoracion(cancion.getValoracion());
            }
            if (cancion.getFechaLanzamiento() != null) {
                cancionTemp.setFechaLanzamiento(cancion.getFechaLanzamiento());
            }
            if (cancion.getActive() != null) {
                cancionTemp.setActive(cancion.getActive());
            }

            return cancionRepository.save(cancionTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cancion no encontrada");
        }
    }

    @DeleteMapping(value = "/cancion/{cid}", produces = "application/json")
    public Cancion deleteCancion(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent() && cancionOptional.get().getActive()) {
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
