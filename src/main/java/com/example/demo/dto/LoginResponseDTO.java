package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private Long id;

    public LoginResponseDTO(Long id) {
        this.id = id;
    }
}
