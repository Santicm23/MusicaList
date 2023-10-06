package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String correo;
    private String password;

    public LoginRequestDTO(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }
}
