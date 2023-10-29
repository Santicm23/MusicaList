package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private Boolean activo = true;
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario", referencedColumnName = "id")
    private TipoUsuario rol = new TipoUsuario(2L);
    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_cancion")
    )
    private List<Cancion> likesDeCanciones = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }
}
