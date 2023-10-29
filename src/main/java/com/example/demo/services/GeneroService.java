package com.example.demo.services;

import com.example.demo.Exceptions.NotFoundRequestException;
import com.example.demo.Exceptions.StandardRequestException;
import com.example.demo.dto.GeneroDTO;
import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    private Genero getGeneroFromDB(Long gid) throws NotFoundRequestException {
        return generoRepository.findById(gid).orElseThrow(
                () -> new NotFoundRequestException(
                        "Género con id " + gid + " no encontrado"
                )
        );
    }

    public List<GeneroDTO> getGeneros() {
        List<Genero> generos = generoRepository.findByActivoTrue();
        return generos.stream().map(GeneroDTO::new).toList();
    }

    public GeneroDTO getGeneroById(Long gid) throws StandardRequestException {
        return new GeneroDTO(getGeneroFromDB(gid));
    }

    public GeneroDTO createGenero(Genero genero) throws StandardRequestException {
        try {
            return new GeneroDTO(generoRepository.save(genero));
        } catch (Exception e) {
            throw new StandardRequestException(
                    "No se pudo crear el género");
        }
    }

    public GeneroDTO updateGenero(Long gid, Genero genero) throws StandardRequestException {
        Genero generoFromDB = getGeneroFromDB(gid);
        BeanUtils.copyProperties(genero, generoFromDB, "activo");
        return new GeneroDTO(generoRepository.save(generoFromDB));
    }

    public GeneroDTO deleteGenero(Long gid) throws StandardRequestException {
        Genero genero = getGeneroFromDB(gid);
        genero.setActivo(false);
        return new GeneroDTO(generoRepository.save(genero));
    }
}
