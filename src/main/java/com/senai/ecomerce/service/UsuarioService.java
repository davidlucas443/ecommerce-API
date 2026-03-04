package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.UsuarioRequestDto;
import com.senai.ecomerce.dto.UsuarioResponseDto;
import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDto create(UsuarioRequestDto dto){
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setNome(dto.getNome());

        usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuario);
    }

}
