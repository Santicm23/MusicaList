package com.example.demo.controllers;

import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.CancionDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.helpers.Hashing;
import com.example.demo.models.Usuario;
import com.example.demo.repostories.UsuarioRepository;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/usuarios", produces = "application/json")
    public List<UsuarioDTO> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(value = "/usuario/{uid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO getUsuarioById(@PathVariable Long uid) throws StandardRequestException {
        return usuarioService.getUsuarioById(uid);
    }

    @PostMapping(value = "/usuario", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO createUsuario(@RequestBody Usuario usuario) throws StandardRequestException {
        return usuarioService.createUsuario(usuario);
    }

    @PostMapping(value = "/login", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDTO) {
        List<Usuario> usuarios = usuarioRepository.findByCorreo(loginDTO.getCorreo());
        if (usuarios.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Usuario o contraseña incorrectos");
        }
        Usuario usuariosTemp = usuarios.get(0);
        if (Hashing.checkPassword(loginDTO.getContrasena(), usuariosTemp.getContrasena()) && usuariosTemp.getActivo())
            return new LoginResponseDTO(usuariosTemp.getId(), usuariosTemp.getRol().getId() == 1L);
        else
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Usuario o contraseña incorrectos");
    }

    @GetMapping(value = "/usuario/{uid}/canciones", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> getCancionesByUsuario(@PathVariable Long uid) throws StandardRequestException {
        return usuarioService.getCancionesByUsuario(uid);
    }

    @PostMapping(value = "/usuario/{uid}/cancion/{cid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO addCancionToUsuario(@PathVariable Long uid, @PathVariable Long cid) throws StandardRequestException {
        return usuarioService.addCancionToUsuario(uid, cid);
    }

    @DeleteMapping(value = "/usuario/{uid}/cancion/{cid}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO deleteCancionFromUsuario(@PathVariable Long uid, @PathVariable Long cid) throws StandardRequestException {
        return usuarioService.deleteCancionFromUsuario(uid, cid);
    }
}
