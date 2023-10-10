package com.example.demo.repostories;

import com.example.demo.models.Cancion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CancionRepository extends CrudRepository<Cancion, Long> {
    Iterable<Cancion> findByGeneroId(Long gid);
    List<Cancion> findByNombreContaining(String nombre);
    List<Cancion> findByAutorContaining(String autor);
    List<Cancion> findByGeneroNombreContaining(String genero);
    List<Cancion> findByAlbumContaining(String album);
}
