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
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Cancion> getCanciones() {
        return cancionRepository.findByActivoTrue();
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
    public List<Cancion> getCancionesByGenero(@PathVariable Long gid) {
        List<Cancion> canciones = cancionRepository.findByGeneroId(gid);
        canciones.removeIf(cancion -> !cancion.getActivo());
        return canciones;
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
        autor.ifPresent(s -> canciones.addAll(cancionRepository.findByArtistaContaining(s)));
        genero.ifPresent(s -> canciones.addAll(cancionRepository.findByGeneroNombreContaining(s)));
        album.ifPresent(s -> canciones.addAll(cancionRepository.findByAlbumContaining(s)));
        canciones.removeIf(cancion -> !cancion.getActivo());
        return canciones;
    }

    @PostMapping(value = "/cancion", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Cancion createCancion(@RequestBody Cancion cancion) {
        try {
            return cancionRepository.save(cancion);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error al crear la cancion");
        }
    }

    @PutMapping(value = "/cancion/{cid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
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
            if (cancion.getArtista() != null) {
                cancionTemp.setArtista(cancion.getArtista());
            }
            if (cancion.getAlbum() != null) {
                cancionTemp.setAlbum(cancion.getAlbum());
            }
            if (cancion.getNumLikes() != null) {
                cancionTemp.setNumLikes(cancion.getNumLikes());
            }
            if (cancion.getFechaLanzamiento() != null) {
                cancionTemp.setFechaLanzamiento(cancion.getFechaLanzamiento());
            }
            if (cancion.getActivo() != null) {
                cancionTemp.setActivo(cancion.getActivo());
            }

            return cancionRepository.save(cancionTemp);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MENSAJE404);
        }
    }

    @DeleteMapping(value = "/cancion/{cid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Cancion deleteCancion(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            Cancion cancionTemp = cancionOptional.get();
            cancionTemp.setActivo(false);
            cancionRepository.save(cancionTemp);

            return cancionTemp;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MENSAJE404);
        }
    }

    @PutMapping(value = "/cancion/{cid}/like", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Cancion likeCancion(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            Cancion cancionTemp = cancionOptional.get();
            cancionTemp.setNumLikes(cancionTemp.getNumLikes() + 1);
            cancionRepository.save(cancionTemp);

            return cancionTemp;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MENSAJE404);
        }
    }

    @PutMapping(value = "/cancion/{cid}/dislike", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public Cancion dislikeCancion(@PathVariable Long cid) {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isPresent()) {
            Cancion cancionTemp = cancionOptional.get();
            cancionTemp.setNumLikes(cancionTemp.getNumLikes() - 1);
            cancionRepository.save(cancionTemp);

            return cancionTemp;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MENSAJE404);
        }
    }
}
