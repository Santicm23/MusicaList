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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping(value = "/buscar/canciones/{filtro}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Cancion> buscarCanciones(
            @PathVariable String filtro
    ) {
        return Stream.of(
                        cancionRepository.findByNombreContaining(filtro),
                        cancionRepository.findByArtistaContaining(filtro),
                        cancionRepository.findByGeneroNombreContaining(filtro),
                        cancionRepository.findByAlbumContaining(filtro))
                .flatMap(List::stream)
                .distinct()
                .filter(Cancion::getActivo)
                .collect(Collectors.toList());
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
            if (cancion.getImagen() != null) {
                cancionTemp.setImagen(cancion.getImagen());
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
}
