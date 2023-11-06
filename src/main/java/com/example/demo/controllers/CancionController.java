package com.example.demo.controllers;

import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.CancionDTO;
import com.example.demo.models.Cancion;
import com.example.demo.services.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CancionController {

    @Autowired
    private CancionService cancionService;

    @GetMapping(value = "/canciones", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> getCanciones() {
        return cancionService.getCanciones();
    }

    @GetMapping(value = "/cancion/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CancionDTO getCancionById(@PathVariable Long cid) throws StandardRequestException {
        return cancionService.getCancionById(cid);
    }

    @GetMapping(value = "/genero/{gid}/canciones", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> getCancionesByGenero(@PathVariable Long gid) {
        return cancionService.getCancionesByGenero(gid);
    }

    @GetMapping(value = "/buscar/canciones/{filtro}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> buscarCanciones(@PathVariable String filtro) {
        return cancionService.buscarCanciones(filtro);
    }

    @PostMapping(value = "/cancion", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public CancionDTO createCancion(@RequestBody Cancion cancion) throws StandardRequestException {
        return cancionService.createCancion(cancion);
    }

    @PutMapping(value = "/cancion/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public CancionDTO updateCancion(@PathVariable Long cid, @RequestBody Cancion cancion) throws StandardRequestException {
        return cancionService.updateCancion(cid, cancion);
    }

    @DeleteMapping(value = "/cancion/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public CancionDTO deleteCancion(@PathVariable Long cid) throws StandardRequestException {
        return cancionService.deleteCancion(cid);
    }
}
