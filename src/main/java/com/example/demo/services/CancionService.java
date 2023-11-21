package com.example.demo.services;

import com.example.demo.exceptions.NotFoundRequestException;
import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.CancionDTO;
import com.example.demo.models.Cancion;
import com.example.demo.repostories.CancionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CancionService {

    CancionRepository cancionRepository;

    @Autowired
    public CancionService(CancionRepository cancionRepository) {
        this.cancionRepository = cancionRepository;
    }

    private Cancion getGeneroFromDB(Long cid) throws StandardRequestException {
        Optional<Cancion> cancionOptional = cancionRepository.findById(cid);

        if (cancionOptional.isEmpty() || !cancionOptional.get().getActivo()) {
            throw new NotFoundRequestException(
                    "Canción con id " + cid + " no encontrada"
            );
        }
        return cancionOptional.get();
    }

    public List<CancionDTO> getCanciones() {
        List<Cancion> canciones = cancionRepository.findByActivoTrue();
        return canciones.stream().filter(Cancion::getActivo).map(CancionDTO::new).toList();
    }

    public CancionDTO getCancionById(Long cid) throws StandardRequestException {
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

    public CancionDTO createCancion(Cancion cancion) throws StandardRequestException {
        try {
            return new CancionDTO(cancionRepository.save(cancion));
        } catch (Exception e) {
            throw new StandardRequestException("No se pudo crear la canción");
        }
    }

    public CancionDTO updateCancion(Long cid, Cancion cancion) throws StandardRequestException {
        Cancion cancionFromDB = getGeneroFromDB(cid);
        BeanUtils.copyProperties(cancion, cancionFromDB, "activo");
        return new CancionDTO(cancionRepository.save(cancionFromDB));
    }

    public CancionDTO deleteCancion(Long cid) throws StandardRequestException {
        Cancion cancion = getGeneroFromDB(cid);
        cancion.setActivo(false);
        return new CancionDTO(cancionRepository.save(cancion));
    }
}
