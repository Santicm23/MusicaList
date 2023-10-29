package com.example.demo.services;

import com.example.demo.dto.GeneroDTO;
import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GeneroService {

    GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    private Genero getGeneroFromDB(Long gid) {
        return generoRepository.findById(gid).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Genero no encontrado")
        );
    }

    public List<GeneroDTO> getGeneros() {
        List<Genero> generos = generoRepository.findByActivoTrue();
        return generos.stream().map(GeneroDTO::new).toList();
    }

    public GeneroDTO getGeneroById(Long gid) {
        return new GeneroDTO(getGeneroFromDB(gid));
    }

    public GeneroDTO createGenero(Genero genero) {
        return new GeneroDTO(generoRepository.save(genero));
    }

    public GeneroDTO updateGenero(Long gid, Genero genero) {
        Genero generoFromDB = getGeneroFromDB(gid);
        BeanUtils.copyProperties(genero, generoFromDB, "activo");
        return new GeneroDTO(generoRepository.save(generoFromDB));
    }

    public GeneroDTO deleteGenero(Long gid) {
        Genero genero = getGeneroFromDB(gid);
        genero.setActivo(false);
        return new GeneroDTO(generoRepository.save(genero));
    }
}
