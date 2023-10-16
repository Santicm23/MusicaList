package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
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
    private String artista;
    private String album;
    private Long numLikes = 0L;
    private Time duracion;
    private Date fechaLanzamiento;
    private Boolean activo = true;
    private String imagen;
    @ManyToOne
    @JoinColumn(name = "id_genero", referencedColumnName = "id")
    private Genero genero;
}
