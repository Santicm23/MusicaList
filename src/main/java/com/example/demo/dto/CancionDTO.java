package com.example.demo.dto;

import com.example.demo.models.Cancion;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CancionDTO {
    private Long id;
    private String nombre;
    private String artista;
    private String album;
    private Long numLikes;
    private String duracion;
    private Date fechaLanzamiento;
    private String imagen;
    private GeneroDTO genero;

    public CancionDTO(Cancion cancion) {
        this.id = cancion.getId();
        this.nombre = cancion.getNombre();
        this.artista = cancion.getArtista();
        this.album = cancion.getAlbum();
        this.numLikes = cancion.getNumLikes();
        this.duracion = cancion.getDuracion().toString();
        this.fechaLanzamiento = cancion.getFechaLanzamiento();
        this.imagen = cancion.getImagen();
        this.genero = new GeneroDTO(cancion.getGenero());
    }
}
