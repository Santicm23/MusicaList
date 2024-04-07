package com.example.demo.dto;

import com.example.demo.models.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long uid;
    private String nombre;
    private String correo;

    public UsuarioDTO(Usuario usuario) {
        this.uid = usuario.getId();
        this.nombre = usuario.getNombre();
        this.correo = usuario.getCorreo();
    }
}
