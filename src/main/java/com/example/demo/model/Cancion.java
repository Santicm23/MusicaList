package com.example.demo.model;

import jakarta.persistence.*;
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
    private Long numLikes;
    private String valoracion;
    private Timestamp duracion;
    private Date fechaLanzamiento;
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "id_genero", referencedColumnName = "id")
    private Genero genero;
}
