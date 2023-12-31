package com.example.demo.services;

import com.example.demo.exceptions.NotFoundRequestException;
import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.GeneroDTO;
import com.example.demo.models.Genero;
import com.example.demo.repostories.GeneroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    private Genero getGeneroFromDB(Long gid) throws NotFoundRequestException {
        Optional<Genero> generoOptional = generoRepository.findById(gid);
        if (generoOptional.isEmpty() || !generoOptional.get().getActivo()) {
            throw new NotFoundRequestException(
                    "Género con id " + gid + " no encontrado"
            );
        }
        return generoOptional.get();
    }

    public List<GeneroDTO> getGeneros() {
        List<Genero> generos = generoRepository.findByActivoTrue();
        return generos.stream().filter(Genero::getActivo).map(GeneroDTO::new).toList();
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
