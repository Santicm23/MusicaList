package com.example.demo.services;

import com.example.demo.exceptions.NotFoundRequestException;
import com.example.demo.exceptions.StandardRequestException;
import com.example.demo.dto.CancionDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.models.Cancion;
import com.example.demo.models.Usuario;
import com.example.demo.repostories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private Usuario getUsuarioFromDB(Long uid) throws NotFoundRequestException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uid);

        if (usuarioOptional.isEmpty() || !usuarioOptional.get().getActivo()) {
            throw new NotFoundRequestException(
                    "Usuario con id " + uid + " no encontrado"
            );
        }
        return usuarioOptional.get();
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
        return usuarios.stream().filter(Usuario::getActivo).map(UsuarioDTO::new).toList();
    }

    public UsuarioDTO getUsuarioById(Long uid) throws StandardRequestException {
        return new UsuarioDTO(getUsuarioFromDB(uid));
    }

    public UsuarioDTO createUsuario(Usuario usuario) throws StandardRequestException {
        try {
            return new UsuarioDTO(usuarioRepository.save(usuario));
        } catch (Exception e) {
            throw new StandardRequestException("No se pudo crear el usuario");
        }
    }

    public List<CancionDTO> getCancionesByUsuario(Long uid) throws StandardRequestException {
        Usuario usuario = getUsuarioFromDB(uid);
        return usuario.getLikesDeCanciones()
                .stream().map(CancionDTO::new).toList();
    }

    public UsuarioDTO addCancionToUsuario(Long uid, Long cid) throws StandardRequestException {
        Usuario usuario = getUsuarioFromDB(uid);
        usuario.getLikesDeCanciones().add(new Cancion(cid));
        return new UsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO deleteCancionFromUsuario(Long uid, Long cid) throws StandardRequestException {
        Usuario usuario = getUsuarioFromDB(uid);
        usuario.getLikesDeCanciones().removeIf(cancion -> Objects.equals(cancion.getId(), cid));
        return new UsuarioDTO(usuarioRepository.save(usuario));
    }
}
