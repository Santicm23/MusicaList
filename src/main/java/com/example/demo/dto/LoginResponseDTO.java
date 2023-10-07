package com.example.demo.dto;

import com.example.demo.models.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private Boolean admin;
}
