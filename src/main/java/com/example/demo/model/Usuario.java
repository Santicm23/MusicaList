package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Boolean activo;
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario", referencedColumnName = "id")
    private TipoUsuario rol;
    @ManyToMany
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_cancion")
    )
    private List<Cancion> likesDeCanciones;
}
