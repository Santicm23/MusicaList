package com.example.demo.dto;

import com.example.demo.models.Genero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneroDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;

    public GeneroDTO(Genero genero) {
        this.id = genero.getId();
        this.nombre = genero.getNombre();
        this.descripcion = genero.getDescripcion();
        this.imagen = genero.getImagen();
    }
}
