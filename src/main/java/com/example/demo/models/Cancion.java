package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String autor;
    private String album;
//    private String portada;
//    private String sonido;
    private Long numLikes = 0L;
    private Long valoracion;
    private Long duracion;
    private Date fechaLanzamiento;
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "id_genero", referencedColumnName = "id")
    private Genero genero;

    public void setAlbumNotNull(String album) {
        if (album != null) {
            this.album = album;
        }
    }

    public void setAutorNotNull(String autor) {
        if (autor != null) {
            this.autor = autor;
        }
    }

    public void setDuracionNotNull(Long duracion) {
        if (duracion != null) {
            this.duracion = duracion;
        }
    }

    public void setFechaLanzamientoNotNull(Date fechaLanzamiento) {
        if (fechaLanzamiento != null) {
            this.fechaLanzamiento = fechaLanzamiento;
        }
    }

    public void setGeneroNotNull(Genero genero) {
        if (genero != null) {
            this.genero = genero;
        }
    }

    public void setNombreNotNull(String nombre) {
        if (nombre != null) {
            this.nombre = nombre;
        }
    }

    public void setNumLikesNotNull(Long numLikes) {
        if (numLikes != null) {
            this.numLikes = numLikes;
        }
    }

    public void setValoracionNotNull(Long valoracion) {
        if (valoracion != null) {
            this.valoracion = valoracion;
        }
    }

    public void setActiveNotNull(Boolean active) {
        if (active != null) {
            this.active = active;
        }
    }

    public void setGeneroNotNull(Long idGenero) {
        if (idGenero != null) {
            this.genero = new Genero();
            this.genero.setId(idGenero);
        }
    }
}
