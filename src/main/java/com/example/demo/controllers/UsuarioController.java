package com.example.demo.controllers;

import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.CancionDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.InfoUsuarioDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.models.Usuario;
import com.example.demo.services.JwtService;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    private final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @GetMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UsuarioDTO> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(value = "/usuario/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO getUsuarioById(
            @RequestHeader(name = AUTHORIZATION_HEADER) String token, @PathVariable Long uid) throws StandardRequestException {
        jwtService.requiresSameId(token, uid);
        return usuarioService.getUsuarioById(uid);
    }

    @PostMapping(value = "/public/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8081"})
    public UsuarioDTO createUsuario(@RequestBody Usuario usuario) throws StandardRequestException {
        return usuarioService.createUsuario(usuario);
    }

    @PostMapping(value = "/public/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8081"})
    public InfoUsuarioDTO login(@RequestBody LoginRequestDTO loginDTO) throws StandardRequestException {
        return usuarioService.login(loginDTO);
    }

    @GetMapping(value = "/usuario/{uid}/canciones", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public List<CancionDTO> getCancionesByUsuario(@PathVariable Long uid) throws StandardRequestException {
        return usuarioService.getCancionesByUsuario(uid);
    }

    @PostMapping(value = "/usuario/{uid}/cancion/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO addCancionToUsuario(
            @RequestHeader(name = AUTHORIZATION_HEADER) String token, @PathVariable Long uid, @PathVariable Long cid) throws StandardRequestException {
        jwtService.requiresSameId(token, uid);
        return usuarioService.addCancionToUsuario(uid, cid);
    }

    @DeleteMapping(value = "/usuario/{uid}/cancion/{cid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public UsuarioDTO deleteCancionFromUsuario(
            @RequestHeader(name = AUTHORIZATION_HEADER) String token, @PathVariable Long uid, @PathVariable Long cid) throws StandardRequestException {
        jwtService.requiresSameId(token, uid);
        return usuarioService.deleteCancionFromUsuario(uid, cid);
    }
}
