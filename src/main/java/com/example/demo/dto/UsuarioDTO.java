package com.example.demo.dto;

import com.example.demo.models.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioDTO {
    private Long uid;
    private String nombre;
    private String correo;
    private List<CancionDTO> canciones;

    public UsuarioDTO(Usuario usuario) {
        this.uid = usuario.getId();
        this.nombre = usuario.getNombre();
        this.correo = usuario.getCorreo();
        this.canciones = usuario.getLikesDeCanciones()
                .stream().map(CancionDTO::new).toList();
    }
}
