package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String autor;
    private String album;
    private String portada;
    private String sonido;
    private Long numLikes;
    private String valoracion;
    private Date fechaPublicacion;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id_genero")
    private Genero genero;
}
