package com.example.demo.controllers;

import com.example.demo.dto.CancionDTO;
import com.example.demo.models.Cancion;
import com.example.demo.services.CancionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CancionController {

    @Autowired
    private CancionService cancionService;

    @GetMapping(value = "/canciones", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> getCanciones() {
        return cancionService.getCanciones();
    }

    @GetMapping(value = "/cancion/{cid}", produces = "application/json")
    public CancionDTO getCancionById(@PathVariable Long cid) {
        return cancionService.getCancionById(cid);
    }

    @GetMapping(value = "/genero/{gid}/canciones", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> getCancionesByGenero(@PathVariable Long gid) {
        return cancionService.getCancionesByGenero(gid);
    }

    @GetMapping(value = "/buscar/canciones/{filtro}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> buscarCanciones(@PathVariable String filtro) {
        return cancionService.buscarCanciones(filtro);
    }

    @PostMapping(value = "/cancion", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public CancionDTO createCancion(@RequestBody Cancion cancion) {
        return cancionService.createCancion(cancion);
    }

    @PutMapping(value = "/cancion/{cid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public CancionDTO updateCancion(@PathVariable Long cid, @RequestBody Cancion cancion) {
        return cancionService.updateCancion(cid, cancion);
    }

    @DeleteMapping(value = "/cancion/{cid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public CancionDTO deleteCancion(@PathVariable Long cid) {
        return cancionService.deleteCancion(cid);
    }
}
