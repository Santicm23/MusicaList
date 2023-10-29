package com.example.demo.services;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.models.Cancion;
import com.example.demo.models.Usuario;
import com.example.demo.repostories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getUsuarioFromDB(Long uid) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uid);

        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }

    public UsuarioDTO getUsuarioById(Long uid) {
        return new UsuarioDTO(getUsuarioFromDB(uid));
    }

    public UsuarioDTO createUsuario(Usuario usuario) {
        try {
            return new UsuarioDTO(usuarioRepository.save(usuario));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se pudo crear el usuario");
        }
    }

    public List<Cancion> getCancionesByUsuario(Long uid) {
        return getUsuarioFromDB(uid).getLikesDeCanciones();
    }

    public UsuarioDTO addCancionToUsuario(Long uid, Long cid) {
        Usuario usuario = getUsuarioFromDB(uid);
        usuario.getLikesDeCanciones().add(new Cancion(cid));
        return new UsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO deleteCancionFromUsuario(Long uid, Long cid) {
        Usuario usuario = getUsuarioFromDB(uid);
        usuario.getLikesDeCanciones().removeIf(cancion -> Objects.equals(cancion.getId(), cid));
        return new UsuarioDTO(usuarioRepository.save(usuario));
    }
}