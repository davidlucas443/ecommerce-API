package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.UsuarioRequestDto;
import com.senai.ecomerce.dto.UsuarioResponseDto;
import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.enums.Roles;
import com.senai.ecomerce.repositories.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDto> findAll() {
        return usuarioRepository.findAll().stream().map(UsuarioResponseDto::new).toList();
    }

    public UsuarioResponseDto findById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return new UsuarioResponseDto(usuario);
    }

    public UsuarioResponseDto create(UsuarioRequestDto dto){
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setRoles(Roles.USER);

        usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuario);
    }

    public UsuarioResponseDto update(UUID id, UsuarioRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        String emailNovo = dto.getEmail();
        if (!usuario.getEmail().equals(emailNovo) && usuarioRepository.existsByEmail(emailNovo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(emailNovo);
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenha(dto.getSenha());

        usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuario);
    }

    public void delete(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário está em uso e não pode ser removido", ex);
        }
    }
}
