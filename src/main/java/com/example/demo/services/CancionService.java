package com.example.demo.services;

import com.example.demo.dto.CancionDTO;
import com.example.demo.models.Cancion;
import com.example.demo.repostories.CancionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CancionService {

    CancionRepository cancionRepository;

    @Autowired
    public CancionService(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    private Cancion getGeneroFromDB(Long gid) {
        return cancionRepository.findById(gid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Genero no encontrado")
        );
    }

    public List<CancionDTO> getCanciones() {
        List<Cancion> canciones = cancionRepository.findByActivoTrue();
        return canciones.stream().map(CancionDTO::new).toList();
    }

    public CancionDTO getCancionById(Long cid) {
        return new CancionDTO(getGeneroFromDB(cid));
    }

    public List<CancionDTO> getCancionesByGenero(Long gid) {
        List<Cancion> canciones = cancionRepository.findByGeneroId(gid);
        canciones.removeIf(cancion -> !cancion.getActivo());
        return canciones.stream().map(CancionDTO::new).toList();
    }

    public List<CancionDTO> buscarCanciones(
            String filtro
    ) {
        return Stream.of(
                        cancionRepository.findByNombreContaining(filtro),
                        cancionRepository.findByArtistaContaining(filtro),
                        cancionRepository.findByGeneroNombreContaining(filtro),
                        cancionRepository.findByAlbumContaining(filtro))
                .flatMap(List::stream)
                .distinct()
                .filter(Cancion::getActivo)
                .map(CancionDTO::new)
                .collect(Collectors.toList());
    }

    public CancionDTO createCancion(Cancion cancion) {
        return new CancionDTO(cancionRepository.save(cancion));
    }

    public CancionDTO updateCancion(Long cid, Cancion cancion) {
        Cancion cancionFromDB = getGeneroFromDB(cid);
        BeanUtils.copyProperties(cancion, cancionFromDB, "activo");
        return new CancionDTO(cancionRepository.save(cancionFromDB));
    }

    public CancionDTO deleteCancion(Long cid) {
        Cancion cancion = getGeneroFromDB(cid);
        cancion.setActivo(false);
        return new CancionDTO(cancionRepository.save(cancion));
    }
}
