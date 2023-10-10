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

    private static final String MENSAJE404 = "Cancion no encontrada";

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
                    HttpStatus.NOT_FOUND, MENSAJE404);
        }
    }

    @GetMapping(value = "/genero/{gid}/canciones", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Iterable<Cancion> getCancionesByGenero(@PathVariable Long gid) {
        return cancionRepository.findByGeneroId(gid);
    }

    @GetMapping(value = "buscar/canciones", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Cancion> buscarCanciones(
            @RequestParam Optional<String> nombre,
            @RequestParam Optional<String> autor,
            @RequestParam Optional<String> genero,
            @RequestParam Optional<String> album
    ) {
        List<Cancion> canciones = new ArrayList<>();
        nombre.ifPresent(s -> canciones.addAll(cancionRepository.findByNombreContaining(s)));
        autor.ifPresent(s -> canciones.addAll(cancionRepository.findByAutorContaining(s)));
        genero.ifPresent(s -> canciones.addAll(cancionRepository.findByGeneroNombreContaining(s)));
        album.ifPresent(s -> canciones.addAll(cancionRepository.findByAlbumContaining(s)));
        return canciones;
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
                    HttpStatus.NOT_FOUND, MENSAJE404);
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
                    HttpStatus.NOT_FOUND, MENSAJE404);
        }
    }
}
